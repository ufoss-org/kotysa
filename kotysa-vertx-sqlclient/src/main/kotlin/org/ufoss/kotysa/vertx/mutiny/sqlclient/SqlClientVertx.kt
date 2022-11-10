/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.vertx.mutiny.sqlclient.Pool
import io.vertx.mutiny.sqlclient.SqlConnection
import io.vertx.mutiny.sqlclient.Transaction
import io.vertx.mutiny.sqlclient.Tuple
import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.postgresql.Tsquery
import org.ufoss.kotysa.vertx.mutiny.sqlclient.transaction.VertxTransaction
import org.ufoss.kotysa.vertx.mutiny.sqlclient.transaction.VertxTransactionalOp
import java.math.BigDecimal

/**
 * @sample org.ufoss.kotysa.vertx.mutiny.sqlclient.sample.UserRepositoryVertx
 */
internal sealed class SqlClientVertx(
    private val pool: Pool,
    override val tables: Tables,
) : DefaultSqlClient, VertxTransactionalOp {

    override val module = Module.VERTX_SQL_CLIENT

    protected fun <T : Any> insertProtected(row: T) =
        pool.getVertxConnection().executeUni { connection ->
            executeInsert(connection, row, tables.getTable(row::class))
        }

    private fun <T : Any> executeInsert(connection: SqlConnection, row: T, table: KotysaTable<T>) =
        connection.preparedQuery(insertSql(row))
            .execute(buildTuple(row, table))
            .replaceWithVoid()

    protected fun <T : Any> insertProtected(rows: Array<T>): Uni<Void> {
        require(rows.isNotEmpty()) { "rows must contain at least one element" }
        val table = tables.getTable(rows[0]::class)
        return pool.getVertxConnection().executeUni { connection ->
            Uni.combine().all().unis<Void>(
                rows.map { row ->
                    executeInsert(connection, row, table)
                }
            ).discardItems()
        }
    }

    protected fun <T : Any> insertAndReturnProtected(row: T): Uni<T> {
        val table = tables.getTable(row::class)

        return pool.getVertxConnection().executeUni { connection ->
            executeInsertAndReturn(connection, row, table)
        }
    }

    private fun <T : Any> executeInsertAndReturn(connection: SqlConnection, row: T, table: KotysaTable<T>) =
        if (tables.dbType == DbType.MYSQL) {
            // For MySQL : insert, then fetch created tuple
            connection.preparedQuery(insertSql(row))
                .execute(buildTuple(row, table))
                .chain { ->
                    fetchLastInserted(connection, row, table)
                }
        } else {
            // other DB types have RETURNING style features
            connection.preparedQuery(insertSql(row, true))
                .execute(buildTuple(row, table))
                .map { rowSet ->
                    val vertxRow = rowSet.iterator().next()
                    (table.table as AbstractTable<T>).toField(
                        tables.allColumns,
                        tables.allTables,
                        tables.dbType,
                    ).builder(vertxRow.toRow())
                }
        }

    protected fun <T : Any> insertAndReturnProtected(rows: Array<out T>): Multi<T> {
        val table = tables.getTable(rows[0]::class)

        return pool.getVertxConnection().executeMulti { connection ->
            Multi.createFrom().items(*rows)
                .onItem().transformToUniAndConcatenate { row -> executeInsertAndReturn(connection, row, table) }
        }
    }

    private fun <T : Any> buildTuple(row: T, table: KotysaTable<T>) =
        table.dbColumns
            // do nothing for null values with default or Serial type
            .filterNot { column ->
                column.entityGetter(row) == null
                        && (column.defaultValue != null
                        || column.isAutoIncrement
                        || SqlType.SERIAL == column.sqlType
                        || SqlType.BIGSERIAL == column.sqlType)
            }
            .fold(Tuple.tuple()) { tuple, column ->
                val value = column.entityGetter(row)
                tuple.addDbValue(value, tables, column.sqlType)
            }

    private fun <T : Any> fetchLastInserted(connection: SqlConnection, row: T, table: KotysaTable<T>): Uni<T> {
        val pkColumns = table.primaryKey.columns
        val pkFirstColumn = pkColumns.elementAt(0)

        return if (
            pkColumns.size == 1 &&
            pkFirstColumn.isAutoIncrement &&
            pkFirstColumn.entityGetter(row) == null
        ) {
            connection.query(lastInsertedSql(row))
                .execute()
        } else {
            // bind all PK values
            connection.preparedQuery(lastInsertedSql(row))
                .execute(
                    pkColumns
                        .fold(Tuple.tuple()) { tuple, column ->
                            val value = column.entityGetter(row)
                            tuple.addValue(tables.getDbValue(value)!!)
                        }
                )
        }.map { rowSet ->
            val vertxRow = rowSet.iterator().next()
            (table.table as AbstractTable<T>).toField(
                tables.allColumns,
                tables.allTables,
                tables.dbType,
            ).builder(vertxRow.toRow())
        }
    }

    protected fun <T : Any> createTableProtected(table: Table<T>): Uni<Void> =
        createTable(table, false)

    protected fun <T : Any> createTableIfNotExistsProtected(table: Table<T>): Uni<Void> =
        createTable(table, true)

    private fun <T : Any> createTable(table: Table<T>, ifNotExists: Boolean): Uni<Void> {
        val createTableResult = createTableSql(table, ifNotExists)
        return pool.getVertxConnection().executeUni { connection ->
            // 1) execute create table
            connection.query(createTableResult.sql)
                .execute()
                .chain { ->
                    if (createTableResult.createIndexes.isNotEmpty()) {
                        Uni.combine().all().unis<Void>(
                            // 2) loop to execute create indexes
                            createTableResult.createIndexes
                                .map { createIndexResult ->
                                    connection.query(createIndexResult.sql)
                                        .execute()
                                        .onFailure { throwable ->
                                            ifNotExists &&
                                                    throwable.message?.contains(
                                                        createIndexResult.name, true
                                                    ) == true
                                        }.recoverWithNull()
                                }
                        ).discardItems()
                    } else {
                        Uni.createFrom().voidItem()
                    }
                }
        }
    }

    protected fun <T : Any> deleteFromProtected(table: Table<T>): MutinySqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        SqlClientDeleteVertx.FirstDelete(pool, tables, table)

    protected fun <T : Any> updateProtected(table: Table<T>): MutinySqlClientDeleteOrUpdate.Update<T> =
        SqlClientUpdateVertx.FirstUpdate(pool, tables, table)

    protected fun <T : Any, U : Any> selectProtected(column: Column<T, U>): MutinySqlClientSelect.FirstSelect<U> =
        SqlClientSelectVertx.Selectable(pool, tables).select(column)

    protected fun <T : Any> selectProtected(table: Table<T>): MutinySqlClientSelect.FirstSelect<T> =
        SqlClientSelectVertx.Selectable(pool, tables).select(table)

    protected fun <T : Any> selectAndBuildProtected(dsl: (ValueProvider) -> T): MutinySqlClientSelect.Fromable<T> =
        SqlClientSelectVertx.Selectable(pool, tables).selectAndBuild(dsl)

    protected fun selectCountProtected(): MutinySqlClientSelect.Fromable<Long> =
        SqlClientSelectVertx.Selectable(pool, tables).selectCount<Any>(null)

    protected fun <T : Any> selectCountProtected(column: Column<*, T>): MutinySqlClientSelect.FirstSelect<Long> =
        SqlClientSelectVertx.Selectable(pool, tables).selectCount(column)

    protected fun <T : Any, U : Any> selectDistinctProtected(column: Column<T, U>)
            : MutinySqlClientSelect.FirstSelect<U> =
        SqlClientSelectVertx.Selectable(pool, tables).selectDistinct(column)

    protected fun <T : Any, U : Any> selectMinProtected(column: MinMaxColumn<T, U>)
            : MutinySqlClientSelect.FirstSelect<U> = SqlClientSelectVertx.Selectable(pool, tables).selectMin(column)

    protected fun <T : Any, U : Any> selectMaxProtected(column: MinMaxColumn<T, U>)
            : MutinySqlClientSelect.FirstSelect<U> = SqlClientSelectVertx.Selectable(pool, tables).selectMax(column)

    protected fun <T : Any, U : Any> selectAvgProtected(column: NumericColumn<T, U>)
            : MutinySqlClientSelect.FirstSelect<BigDecimal> =
        SqlClientSelectVertx.Selectable(pool, tables).selectAvg(column)

    protected fun <T : Any> selectSumProtected(column: IntColumn<T>): MutinySqlClientSelect.FirstSelect<Long> =
        SqlClientSelectVertx.Selectable(pool, tables).selectSum(column)

    protected fun selectTsRankCdProtected(
        tsvectorColumn: TsvectorColumn<*>,
        tsquery: Tsquery,
    ): MutinySqlClientSelect.FirstSelect<Float> =
        SqlClientSelectVertx.Selectable(pool, tables).selectTsRankCd(tsvectorColumn, tsquery)

    protected fun <T : Any> selectProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): MutinySqlClientSelect.FirstSelect<T> = SqlClientSelectVertx.Selectable(pool, tables).select(dsl)

    protected fun <T : Any> selectCaseWhenExistsProtected(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): MutinySqlClientSelect.SelectCaseWhenExistsFirst<T> =
        SqlClientSelectVertx.Selectable(pool, tables).selectCaseWhenExists(dsl)

    protected fun <T : Any> selectStarFromProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): MutinySqlClientSelect.From<T> = SqlClientSelectVertx.Selectable(pool, tables).selectStarFromSubQuery(dsl)

    protected fun <T : Any> transactionalProtected(block: (VertxTransaction) -> Uni<T>): Uni<T> =
        // reuse currentTransaction if any, else create new transaction from new established connection
        Uni.createFrom().context { context ->
            var isOrigin = false
            if (context.contains(VertxTransaction.ContextKey)
                && !context.get<VertxTransaction>(VertxTransaction.ContextKey).isCompleted()
            ) {
                val vertxTransaction: VertxTransaction = context[VertxTransaction.ContextKey]
                Uni.createFrom().item(vertxTransaction)
            } else {
                isOrigin = true
                pool.connection
                    .map { connection -> VertxTransaction(connection) }
            }
                .flatMap { vertxTransaction ->
                    vertxTransaction.connection.begin()
                        // invoke bloc
                        .flatMap { transaction ->
                            block(vertxTransaction)
                                .withContext { uni, context ->
                                    if (isOrigin) {
                                        context.put(VertxTransaction.ContextKey, vertxTransaction)
                                    }
                                    // fixme one day : this is ugly
                                    Uni.createFrom().emitter<T> { uniEmitter ->
                                        uni.subscribe().with(
                                            context,
                                            { item -> uniEmitter.complete(item) },
                                            { throwable -> uniEmitter.fail(throwable) }
                                        )
                                    }
                                }
                                .onTermination().call { _, throwable, _ ->
                                    finishTransaction(isOrigin, vertxTransaction, transaction, throwable)
                                }
                        }
                }
        }

    protected fun <T : Any> transactionalProtected(block: (VertxTransaction) -> Multi<T>): Multi<T> =
        // reuse currentTransaction if any, else create new transaction from new established connection
        Multi.createFrom().context { context ->
            var isOrigin = false
            if (context.contains(VertxTransaction.ContextKey)) {
                val vertxTransaction: VertxTransaction = context[VertxTransaction.ContextKey]
                Uni.createFrom().item(vertxTransaction)
            } else {
                isOrigin = true
                pool.connection
                    .map { connection -> VertxTransaction(connection) }
            }.onItem().transformToMulti { vertxTransaction ->
                vertxTransaction.connection.begin()
                    // invoke bloc
                    .onItem().transformToMulti { transaction ->
                        block(vertxTransaction)
                            .withContext { multi, context ->
                                if (isOrigin) {
                                    context.put(VertxTransaction.ContextKey, vertxTransaction)
                                }
                                // fixme one day : this is ugly
                                Multi.createFrom().emitter<T> { multiEmitter ->
                                    multi.subscribe().with(
                                        context,
                                        { item -> multiEmitter.emit(item) },
                                        { throwable -> multiEmitter.fail(throwable) },
                                        { multiEmitter.complete() }
                                    )
                                }
                            }
                            .onTermination().call { throwable, _ ->
                                finishTransaction(isOrigin, vertxTransaction, transaction, throwable)
                            }
                    }
            }
        }

    private fun finishTransaction(
        isOrigin: Boolean,
        vertxTransaction: VertxTransaction,
        transaction: Transaction,
        throwable: Throwable?,
    ) =
        // For original transaction only : commit or rollback, then close connection
        if (isOrigin) {
            if (vertxTransaction.isRollbackOnly() || throwable != null) {
                transaction.rollback()
            } else {
                transaction.commit()
            }.chain { ->
                vertxTransaction.setCompleted()
                vertxTransaction.connection.close()
            }
        } else {
            Uni.createFrom().voidItem()
        }
}

internal class MysqlSqlClientVertx internal constructor(
    pool: Pool,
    tables: MysqlTables,
) : SqlClientVertx(pool, tables), MysqlVertxSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun <T : Any> transactional(block: (VertxTransaction) -> Uni<T>) = transactionalProtected(block)
    override fun <T : Any> transactionalMulti(block: (VertxTransaction) -> Multi<T>) = transactionalProtected(block)
}

internal class PostgresqlSqlClientVertx internal constructor(
    pool: Pool,
    tables: PostgresqlTables,
) : SqlClientVertx(pool, tables), PostgresqlVertxSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun selectTsRankCd(
        tsvectorColumn: TsvectorColumn<*>,
        tsquery: Tsquery,
    ) = selectTsRankCdProtected(tsvectorColumn, tsquery)

    override fun <T : Any> transactional(block: (VertxTransaction) -> Uni<T>) = transactionalProtected(block)
    override fun <T : Any> transactionalMulti(block: (VertxTransaction) -> Multi<T>) = transactionalProtected(block)
}

internal class MssqlSqlClientVertx internal constructor(
    pool: Pool,
    tables: MssqlTables,
) : SqlClientVertx(pool, tables), MssqlVertxSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun <T : Any> transactional(block: (VertxTransaction) -> Uni<T>) = transactionalProtected(block)
    override fun <T : Any> transactionalMulti(block: (VertxTransaction) -> Multi<T>) = transactionalProtected(block)
}

internal class MariadbSqlClientVertx internal constructor(
    pool: Pool,
    tables: MariadbTables,
) : SqlClientVertx(pool, tables), MariadbVertxSqlClient {
    override fun <T : Any> insert(row: T) = insertProtected(row)
    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
    override fun selectCount() = selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun <T : Any> transactional(block: (VertxTransaction) -> Uni<T>) = transactionalProtected(block)
    override fun <T : Any> transactionalMulti(block: (VertxTransaction) -> Multi<T>) = transactionalProtected(block)
}

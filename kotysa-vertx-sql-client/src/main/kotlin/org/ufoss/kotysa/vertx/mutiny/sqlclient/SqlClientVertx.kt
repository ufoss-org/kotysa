/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.vertx.mutiny.sqlclient.Pool
import io.vertx.mutiny.sqlclient.SqlConnection
import io.vertx.mutiny.sqlclient.Tuple
import org.ufoss.kotysa.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.transaction.VertxTransaction
import org.ufoss.kotysa.vertx.mutiny.sqlclient.transaction.VertxTransactionalOp
import java.sql.SQLException

/**
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
internal sealed class SqlClientVertx(
    private val pool: Pool,
    override val tables: Tables,
) : DefaultSqlClient, VertxTransactionalOp {

    override val module = Module.VERTX_SQL_CLIENT

    protected fun <T : Any> insertProtected(row: T) =
        getVertxConnection(pool).executeUni { connection ->
            executeInsert(connection, row, tables.getTable(row::class))
        }

    private fun <T : Any> executeInsert(connection: SqlConnection, row: T, table: KotysaTable<T>) =
        connection.preparedQuery(insertSql(row))
            .execute(buildTuple(row, table))
            .replaceWithVoid()

    protected fun <T : Any> insertProtected(rows: Array<T>): Uni<Void> {
        require(rows.isNotEmpty()) { "rows must contain at least one element" }
        val table = tables.getTable(rows[0]::class)
        return getVertxConnection(pool).executeUni { connection ->
            Uni.combine().all().unis<Void>(
                rows.map { row ->
                    executeInsert(connection, row, table)
                }
            ).discardItems()
        }
    }

    protected fun <T : Any> insertAndReturnProtected(row: T): Uni<T> {
        val table = tables.getTable(row::class)

        return getVertxConnection(pool).executeUni { connection ->
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

        return getVertxConnection(pool).executeMulti { connection ->
            Multi.createFrom().items(*rows)
                .onItem().transformToUniAndConcatenate { row -> executeInsertAndReturn(connection, row, table) }
        }
    }

    private fun <T : Any> buildTuple(row: T, table: KotysaTable<T>) =
        table.columns
            // do nothing for null values with default or Serial type
            .filterNot { column ->
                column.entityGetter(row) == null
                        && (column.defaultValue != null
                        || column.isAutoIncrement
                        || SqlType.SERIAL == column.sqlType
                        || SqlType.BIGSERIAL == column.sqlType)
            }
            .fold(Tuple.tuple()) { tuple, column ->
                tuple.addValue(tables.getDbValue(column.entityGetter(row)))
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
        return getVertxConnection(pool).executeUni { connection ->
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
                                            ifNotExists && throwable is SQLException
                                                    && throwable.message?.contains(
                                                createIndexResult.name, true
                                            ) != true
                                        }.recoverWithNull()
                                }
                        ).discardItems()
                    } else {
                        Uni.createFrom().voidItem()
                    }
                }
        }
    }

//    protected fun <T : Any> deleteFromProtected(table: Table<T>): SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
//        SqlClientDeleteJdbc.FirstDelete(getJdbcConnection(), tables, table)
//
//    protected fun <T : Any> updateProtected(table: Table<T>): SqlClientDeleteOrUpdate.Update<T> =
//        SqlClientUpdateJdbc.FirstUpdate(getJdbcConnection(), tables, table)
//
//    protected fun <T : Any, U : Any> selectProtected(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
//        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).select(column)
//
//    protected fun <T : Any> selectProtected(table: Table<T>): SqlClientSelect.FirstSelect<T> =
//        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).select(table)
//
//    protected fun <T : Any> selectAndBuildProtected(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> =
//        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectAndBuild(dsl)
//
//    protected fun selectCountProtected(): SqlClientSelect.Fromable<Long> =
//        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectCount<Any>(null)
//
//    protected fun <T : Any> selectCountProtected(column: Column<*, T>): SqlClientSelect.FirstSelect<Long> =
//        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectCount(column)
//
//    protected fun <T : Any, U : Any> selectDistinctProtected(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
//        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectDistinct(column)
//
//    protected fun <T : Any, U : Any> selectMinProtected(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
//        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectMin(column)
//
//    protected fun <T : Any, U : Any> selectMaxProtected(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
//        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectMax(column)
//
//    protected fun <T : Any, U : Any> selectAvgProtected(column: NumericColumn<T, U>): SqlClientSelect.FirstSelect<BigDecimal> =
//        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectAvg(column)
//
//    protected fun <T : Any> selectSumProtected(column: IntColumn<T>): SqlClientSelect.FirstSelect<Long> =
//        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectSum(column)
//
//    protected fun <T : Any> selectProtected(
//        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
//    ): SqlClientSelect.FirstSelect<T> = SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).select(dsl)
//
//    protected fun <T : Any> selectCaseWhenExistsProtected(
//        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
//    ): SqlClientSelect.SelectCaseWhenExistsFirst<T> =
//        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectCaseWhenExists(dsl)
//
//    protected fun <T : Any> selectStarFromProtected(
//        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
//    ): SqlClientSelect.From<T> = SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectStarFromSubQuery(dsl)

    protected fun <T : Any> transactionalProtected(block: (VertxTransaction) -> Uni<T>): Uni<T> =
        // reuse currentTransaction if any, else create new transaction from new established connection
        Uni.createFrom().context { context ->
            var isOrigin = false
            if (context.contains(VertxTransaction.ContextKey)) {
                isOrigin = true
                val vertxTransaction: VertxTransaction = context[VertxTransaction.ContextKey]
                Uni.createFrom().item(vertxTransaction)
            } else {
                pool.connection
                    .map { connection ->
                        VertxTransaction(connection).apply {
                            context.put(VertxTransaction.ContextKey, this)
                        }
                    }
            }.flatMap { vertxTransaction ->
                vertxTransaction.connection.begin()
                    // invoke bloc
                    .flatMap { transaction ->
                        block(vertxTransaction)
                            .onTermination().call { _, throwable, _ ->
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
                    }
            }
        }

    protected fun <T : Any> transactionalProtected(block: (VertxTransaction) -> Multi<T>): Multi<T> =
        // reuse currentTransaction if any, else create new transaction from new established connection
        Multi.createFrom().context { context ->
            var isOrigin = false
            if (context.contains(VertxTransaction.ContextKey)) {
                isOrigin = true
                val vertxTransaction: VertxTransaction = context[VertxTransaction.ContextKey]
                Uni.createFrom().item(vertxTransaction)
            } else {
                pool.connection
                    .map { connection ->
                        VertxTransaction(connection).apply {
                            context.put(VertxTransaction.ContextKey, this)
                        }
                    }
            }.toMulti()
                .flatMap { vertxTransaction ->
                vertxTransaction.connection.begin()
                    .toMulti()
                    // invoke bloc
                    .flatMap { transaction ->
                        block(vertxTransaction)
                            .onTermination().call { throwable, _ ->
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
                    }
            }
        }
}

//internal class MysqlSqlClientJdbc internal constructor(
//    dataSource: DataSource,
//    tables: MysqlTables,
//) : SqlClientJdbc(dataSource, tables), MysqlJdbcSqlClient {
//    override fun <T : Any> insert(row: T) = insertProtected(row)
//    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
//    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
//    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
//    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
//    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
//    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
//    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
//    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
//    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
//    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
//    override fun selectCount() = selectCountProtected()
//    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
//    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
//    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
//    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
//    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
//    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
//    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)
//
//    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
//        selectCaseWhenExistsProtected(dsl)
//
//    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
//        selectStarFromProtected(dsl)
//
//    override fun <U> transactional(block: (JdbcTransaction) -> U) = transactionalProtected(block)
//}

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
    override fun <T : Any> deleteFrom(table: Table<T>) = TODO() // deleteFromProtected(table)
    override fun <T : Any> update(table: Table<T>) = TODO() // updateProtected(table)
    override fun <T : Any, U : Any> select(column: Column<T, U>) = TODO() // selectProtected(column)
    override fun <T : Any> select(table: Table<T>) = TODO() // selectProtected(table)
    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = TODO() // selectAndBuildProtected(dsl)
    override fun selectCount() = TODO() // selectCountProtected()
    override fun <T : Any> selectCount(column: Column<*, T>) = TODO() // selectCountProtected(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = TODO() // selectDistinctProtected(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = TODO() // selectMinProtected(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = TODO() // selectMaxProtected(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = TODO() // selectAvgProtected(column)
    override fun <T : Any> selectSum(column: IntColumn<T>) = TODO() // selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = TODO() // selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        TODO() // selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        TODO() // selectStarFromProtected(dsl)

    override fun <T : Any> transactionalUni(block: (VertxTransaction) -> Uni<T>) = transactionalProtected(block)
    override fun <T : Any> transactionalMulti(block: (VertxTransaction) -> Multi<T>) = transactionalProtected(block)
}

//internal class MssqlSqlClientJdbc internal constructor(
//    dataSource: DataSource,
//    tables: MssqlTables,
//) : SqlClientJdbc(dataSource, tables), MssqlJdbcSqlClient {
//    override fun <T : Any> insert(row: T) = insertProtected(row)
//    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
//    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
//    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
//    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
//    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
//    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
//    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
//    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
//    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
//    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
//    override fun selectCount() = selectCountProtected()
//    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
//    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
//    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
//    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
//    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
//    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
//    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)
//
//    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
//        selectCaseWhenExistsProtected(dsl)
//
//    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
//        selectStarFromProtected(dsl)
//
//    override fun <U> transactional(block: (JdbcTransaction) -> U) = transactionalProtected(block)
//}
//
//internal class MariadbSqlClientJdbc internal constructor(
//    dataSource: DataSource,
//    tables: MariadbTables,
//) : SqlClientJdbc(dataSource, tables), MariadbJdbcSqlClient {
//    override fun <T : Any> insert(row: T) = insertProtected(row)
//    override fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
//    override fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
//    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
//    override fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
//    override fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
//    override fun <T : Any> deleteFrom(table: Table<T>) = deleteFromProtected(table)
//    override fun <T : Any> update(table: Table<T>) = updateProtected(table)
//    override fun <T : Any, U : Any> select(column: Column<T, U>) = selectProtected(column)
//    override fun <T : Any> select(table: Table<T>) = selectProtected(table)
//    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T) = selectAndBuildProtected(dsl)
//    override fun selectCount() = selectCountProtected()
//    override fun <T : Any> selectCount(column: Column<*, T>) = selectCountProtected(column)
//    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>) = selectDistinctProtected(column)
//    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>) = selectMinProtected(column)
//    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>) = selectMaxProtected(column)
//    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>) = selectAvgProtected(column)
//    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
//    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)
//
//    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
//        selectCaseWhenExistsProtected(dsl)
//
//    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
//        selectStarFromProtected(dsl)
//
//    override fun <U> transactional(block: (JdbcTransaction) -> U) = transactionalProtected(block)
//}

internal fun getVertxConnection(pool: Pool) =
    // reuse currentTransaction's connection if any, else establish a new connection
    Uni.createFrom().context { context ->
        if (context.contains(VertxTransaction.ContextKey)) {
            val vertxTransaction: VertxTransaction = context[VertxTransaction.ContextKey]
            return@context Uni.createFrom().item(
                VertxConnection(vertxTransaction.connection, true)
            )
        }
        pool.connection
            .map { connection -> VertxConnection(connection, false) }
    }
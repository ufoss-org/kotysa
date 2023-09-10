/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import io.vertx.core.Future
import io.vertx.core.json.JsonArray
import io.vertx.kotlin.coroutines.await
import io.vertx.oracleclient.OracleClient
import io.vertx.oracleclient.OraclePrepareOptions
import io.vertx.sqlclient.*
import io.vertx.sqlclient.Row
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.postgresql.Tsquery
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.vertx.transaction.CoroutinesVertxTransaction
import java.lang.reflect.UndeclaredThrowableException
import java.math.BigDecimal
import kotlin.coroutines.coroutineContext

/**
 * @sample org.ufoss.kotysa.vertx.samples.UserRepositoryVertxCoroutines
 */
internal sealed class CoroutinesSqlClientVertx(
    private val pool: Pool,
    override val tables: Tables,
) : DefaultSqlClient {

    override val module = Module.VERTX_SQL_CLIENT

    protected suspend fun <T : Any> insertProtected(row: T) {
        pool.getVertxConnection().execute { connection ->
            executeInsert(connection, row, tables.getTable(row::class))
        }
    }

    private suspend fun <T : Any> executeInsert(connection: SqlConnection, row: T, table: KotysaTable<T>) =
        connection.preparedQuery(insertSql(row))
            .execute(buildTuple(row, table))
            .await()

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

    protected suspend fun <T : Any> insertProtected(rows: Array<T>) {
        require(rows.isNotEmpty()) { "rows must contain at least one element" }
        val table = tables.getTable(rows[0]::class)
        pool.getVertxConnection().execute { connection ->
            rows.forEach { row -> executeInsert(connection, row, table) }
        }
    }

    protected suspend fun <T : Any> insertAndReturnProtected(row: T): T {
        val table = tables.getTable(row::class)

        return pool.getVertxConnection().execute { connection ->
            executeInsertAndReturn(connection, row, table)
        }
    }

    private suspend fun <T : Any> executeInsertAndReturn(connection: SqlConnection, row: T, table: KotysaTable<T>) =
        if (tables.dbType == DbType.MYSQL) {
            // For MySQL : insert, then fetch created tuple
            connection.preparedQuery(insertSql(row))
                .execute(buildTuple(row, table))
                .await()
            fetchLastInserted(connection, row, table)
        } else {
            // other DB types have RETURNING style features
            val sql = insertSql(row, true)
            val preparedQuery = if (tables.dbType == DbType.ORACLE) {
                // Retrieve generated key column value by name
                val autoGeneratedKeysIndexes = table.columns
                    .map { column -> column.name }
                    .fold(JsonArray()) { jsonArray, columnName -> jsonArray.add(columnName) }
                val options = OraclePrepareOptions()
                    .setAutoGeneratedKeysIndexes(autoGeneratedKeysIndexes)
                connection.preparedQuery(sql, options)
            } else {
                connection.preparedQuery(sql)
            }

            preparedQuery.execute(buildTuple(row, table))
                .map { rowSet ->
                    val vertxRow = if (tables.dbType == DbType.ORACLE) {
                        rowSet.property(OracleClient.GENERATED_KEYS)
                    } else {
                        rowSet.iterator().next()
                    }
                    (table.table as AbstractTable<T>).toField(
                        tables.allColumns,
                        tables.allTables,
                        tables.dbType,
                    ).builder(vertxRow.toRow())
                }.await()
        }

    private suspend fun <T : Any> fetchLastInserted(connection: SqlConnection, row: T, table: KotysaTable<T>): T {
        val pkColumns = table.primaryKey.columns
        val pkFirstColumn = pkColumns.elementAt(0)

        val rowSet = if (
            pkColumns.size == 1 &&
            pkFirstColumn.isAutoIncrement &&
            pkFirstColumn.entityGetter(row) == null
        ) {
            connection.query(lastInsertedSql(row))
                .execute()
                .await()
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
                .await()
        }
        val vertxRow = rowSet.iterator().next()

        return (table.table as AbstractTable<T>).toField(
            tables.allColumns,
            tables.allTables,
            tables.dbType,
        ).builder(vertxRow.toRow())
    }

    protected fun <T : Any> insertAndReturnProtected(rows: Array<T>): Flow<T> {
        require(rows.isNotEmpty()) { "rows must contain at least one element" }
        val table = tables.getTable(rows[0]::class)
        return flow {
            val r2dbcConnection = pool.getVertxConnection()
            try {
                emitAll(rows.asFlow()
                    .map { row -> executeInsertAndReturn(r2dbcConnection.connection, row, table) }
                )
            } finally {
                r2dbcConnection.apply {
                    if (!inTransaction) {
                        connection.close().await()
                    }
                }
            }
        }
    }

    protected suspend fun <T : Any> createTableProtected(table: Table<T>) {
        createTable(table, false)
    }

    protected suspend fun <T : Any> createTableIfNotExistsProtected(table: Table<T>) {
        createTable(table, true)
    }

    private suspend fun <T : Any> createTable(table: Table<T>, ifNotExists: Boolean) {
        val createTableResult = createTableSql(table, ifNotExists)
        pool.getVertxConnection().execute { connection ->
            // 1) execute create table
            connection.query(createTableResult.sql)
                .execute()
                .await()

            // 2) loop to execute create indexes
            createTableResult.createIndexes.forEach { createIndexResult ->
                connection.query(createIndexResult.sql)
                    .execute()
                    .recover { throwable ->
                        if (ifNotExists
                            && throwable.message?.contains(createIndexResult.name, true) == true
                        ) {
                            Future.succeededFuture<RowSet<Row>>()
                        } else {
                            Future.failedFuture<RowSet<Row>>(throwable)
                        }
                    }
                    .await()
            }
        }
    }

    protected fun <T : Any> deleteFromProtected(table: Table<T>)
            : CoroutinesSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        CoroutinesSqlClientDeleteVertx.FirstDelete(pool, tables, table)

    protected fun <T : Any> updateProtected(table: Table<T>): CoroutinesSqlClientDeleteOrUpdate.Update<T> =
        CoroutinesSqlClientUpdateVertx.FirstUpdate(pool, tables, table)

    protected fun <T : Any, U : Any> selectProtected(column: Column<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).select(column)

    protected fun <T : Any> selectProtected(table: Table<T>): CoroutinesSqlClientSelect.FirstSelect<T> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).select(table)

    protected fun <T : Any> selectAndBuildProtected(dsl: (ValueProvider) -> T): CoroutinesSqlClientSelect.Fromable<T> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).selectAndBuild(dsl)

    protected fun selectCountProtected(): CoroutinesSqlClientSelect.Fromable<Long> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).selectCount<Any>(null)

    protected fun <T : Any> selectCountProtected(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).selectCount(column)

    protected fun <T : Any, U : Any> selectDistinctProtected(column: Column<T, U>)
            : CoroutinesSqlClientSelect.FirstSelect<U> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).selectDistinct(column)

    protected fun <T : Any, U : Any> selectMinProtected(column: MinMaxColumn<T, U>)
            : CoroutinesSqlClientSelect.FirstSelect<U> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).selectMin(column)

    protected fun <T : Any, U : Any> selectMaxProtected(column: MinMaxColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).selectMax(column)

    protected fun <T : Any, U : Any> selectAvgProtected(column: NumericColumn<T, U>)
            : CoroutinesSqlClientSelect.FirstSelect<BigDecimal> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).selectAvg(column)

    protected fun <T : Any, U : Any> selectSumProtected(column: WholeNumberColumn<T, U>)
            : CoroutinesSqlClientSelect.FirstSelect<Long> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).selectSum(column)

    protected fun selectTsRankCdProtected(
        tsvectorColumn: TsvectorColumn<*>,
        tsquery: Tsquery,
    ): CoroutinesSqlClientSelect.FirstSelect<Float> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).selectTsRankCd(tsvectorColumn, tsquery)

    protected fun <T : Any> selectProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): CoroutinesSqlClientSelect.FirstSelect<T> = CoroutinesSqlClientSelectVertx.Selectable(pool, tables).select(dsl)

    protected fun <T : Any> selectCaseWhenExistsProtected(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): CoroutinesSqlClientSelect.SelectCaseWhenExistsFirst<T> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).selectCaseWhenExists(dsl)

    protected fun <T : Any> selectStarFromProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): CoroutinesSqlClientSelect.From<T> =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).selectStarFromSubQuery(dsl)

    protected fun selectsProtected(): CoroutinesSqlClientSelect.Selects =
        CoroutinesSqlClientSelectVertx.Selectable(pool, tables).selects()

    protected suspend fun <T> transactionalProtected(block: suspend (Transaction) -> T): T? {
        // reuse currentTransaction if any, else create new transaction from new established connection
        val currentTransaction = coroutineContext[CoroutinesVertxTransaction]
        val isOrigin = currentTransaction == null
        var context = coroutineContext
        val vertxTransaction = if (currentTransaction != null && !currentTransaction.isCompleted()) {
            currentTransaction
        } else {
            // if new transaction : add it to coroutineContext
            CoroutinesVertxTransaction(pool.connection.await())
                .apply { context += this }
        }
        var throwable: Throwable? = null

        // use transaction's Connection
        return with(vertxTransaction.connection) {
            val transaction = begin().await()

            try {
                val result = try {
                    withContext(context) {
                        block.invoke(vertxTransaction)
                    }
                } catch (ex: RuntimeException) { // DatabaseException from VertX is a RuntimeException
                    throwable = ex
                    throw ex
                } catch (ex: Error) {
                    throwable = ex
                    throw ex
                } catch (ex: Throwable) {
                    // Transactional block threw unexpected exception
                    throwable = ex
                    throw UndeclaredThrowableException(ex, "block threw undeclared checked exception")
                }

                result
            } finally {
                // For original transaction only : commit or rollback, then close connection
                if (isOrigin) {
                    try {
                        if (vertxTransaction.isRollbackOnly() || throwable != null) {
                            transaction.rollback()
                        } else {
                            transaction.commit()
                        }.await()
                    } finally {
                        try {
                            vertxTransaction.setCompleted()
                            vertxTransaction.connection.close().await()
                        } catch (_: Throwable) {
                            // ignore exception of connection.close()
                        }
                    }
                }
            }
        }
    }
}

internal class MysqlCoroutinesSqlClientVertx internal constructor(
    pool: Pool,
    tables: MysqlTables,
) : CoroutinesSqlClientVertx(pool, tables), MysqlCoroutinesVertxSqlClient {
    override suspend fun <T : Any> insert(row: T) = insertProtected(row)
    override suspend fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override suspend fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override suspend fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
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
    override fun <T : Any, U : Any> selectSum(column: WholeNumberColumn<T, U>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun selects() = selectsProtected()

    override suspend fun <U> transactional(block: suspend (Transaction) -> U) = transactionalProtected(block)
}

internal class PostgresqlCoroutinesSqlClientVertx internal constructor(
    pool: Pool,
    tables: PostgresqlTables,
) : CoroutinesSqlClientVertx(pool, tables), PostgresqlCoroutinesVertxSqlClient {
    override suspend fun <T : Any> insert(row: T) = insertProtected(row)
    override suspend fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override suspend fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override suspend fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
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
    override fun <T : Any, U : Any> selectSum(column: WholeNumberColumn<T, U>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun selects() = selectsProtected()

    override fun selectTsRankCd(
        tsvectorColumn: TsvectorColumn<*>,
        tsquery: Tsquery,
    ) = selectTsRankCdProtected(tsvectorColumn, tsquery)

    override suspend fun <U> transactional(block: suspend (Transaction) -> U) = transactionalProtected(block)
}

internal class MssqlCoroutinesSqlClientVertx internal constructor(
    pool: Pool,
    tables: MssqlTables,
) : CoroutinesSqlClientVertx(pool, tables), MssqlCoroutinesVertxSqlClient {
    override suspend fun <T : Any> insert(row: T) = insertProtected(row)
    override suspend fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override suspend fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override suspend fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
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
    override fun <T : Any, U : Any> selectSum(column: WholeNumberColumn<T, U>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun selects() = selectsProtected()

    override suspend fun <U> transactional(block: suspend (Transaction) -> U) = transactionalProtected(block)
}

internal class MariadbCoroutinesSqlClientVertx internal constructor(
    pool: Pool,
    tables: MariadbTables,
) : CoroutinesSqlClientVertx(pool, tables), MariadbCoroutinesVertxSqlClient {
    override suspend fun <T : Any> insert(row: T) = insertProtected(row)
    override suspend fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override suspend fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override suspend fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
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
    override fun <T : Any, U : Any> selectSum(column: WholeNumberColumn<T, U>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun selects() = selectsProtected()

    override suspend fun <U> transactional(block: suspend (Transaction) -> U) = transactionalProtected(block)
}

internal class OracleCoroutinesSqlClientVertx internal constructor(
    pool: Pool,
    tables: OracleTables,
) : CoroutinesSqlClientVertx(pool, tables), OracleCoroutinesVertxSqlClient {
    override suspend fun <T : Any> insert(row: T) = insertProtected(row)
    override suspend fun <T : Any> insert(vararg rows: T) = insertProtected(rows)
    override suspend fun <T : Any> insertAndReturn(row: T) = insertAndReturnProtected(row)
    override fun <T : Any> insertAndReturn(vararg rows: T) = insertAndReturnProtected(rows)
    override suspend fun <T : Any> createTable(table: Table<T>) = createTableProtected(table)
    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) = createTableIfNotExistsProtected(table)
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
    override fun <T : Any, U : Any> selectSum(column: WholeNumberColumn<T, U>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override fun selects() = selectsProtected()

    override suspend fun <U> transactional(block: suspend (Transaction) -> U) = transactionalProtected(block)
}

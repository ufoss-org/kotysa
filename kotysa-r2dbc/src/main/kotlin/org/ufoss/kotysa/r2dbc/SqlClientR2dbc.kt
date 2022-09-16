/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.R2dbcBadGrammarException
import io.r2dbc.spi.Statement
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.*
import kotlinx.coroutines.withContext
import org.ufoss.kotysa.*
import org.ufoss.kotysa.core.r2dbc.toRow
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransactionImpl
import java.lang.reflect.UndeclaredThrowableException
import java.math.BigDecimal
import java.sql.SQLException
import kotlin.coroutines.coroutineContext
import kotlin.reflect.KClass


/**
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
internal sealed class SqlClientR2dbc(
    private val connectionFactory: ConnectionFactory,
    override val tables: Tables,
) : DefaultSqlClient {

    override val module = Module.R2DBC

    protected suspend fun <T : Any> insertProtected(row: T) {
        val table = tables.getTable(row::class)

        getR2dbcConnection(connectionFactory).execute { connection ->
            val statement = connection.createStatement(insertSql(row))
            setStatementParams(row, table, statement)

            statement.execute().awaitSingle().rowsUpdated.awaitSingle()
        }
    }

    protected suspend fun <T : Any> insertProtected(rows: Array<T>) {
        require(rows.isNotEmpty()) { "rows must contain at least one element" }
        val table = tables.getTable(rows[0]::class)

        getR2dbcConnection(connectionFactory).execute { connection ->
            val statement = connection.createStatement(insertSql(rows[0]))
            rows.forEachIndexed { index, row ->
                setStatementParams(row, table, statement)
                // batch statement
                if (index < rows.size - 1) {
                    statement.add()
                }
            }

            statement.execute().asFlow()
                .map { r -> r.rowsUpdated.awaitFirst() }
                .last()
        }
    }

    protected suspend fun <T : Any> insertAndReturnProtected(row: T): T {
        val table = tables.getTable(row::class)

        return getR2dbcConnection(connectionFactory).execute { connection ->
            executeInsertAndReturn(connection, row, table)
        }
    }

    private suspend fun <T : Any> executeInsertAndReturn(connection: Connection, row: T, table: KotysaTable<T>) =
        if (tables.dbType == DbType.MYSQL) {
            // For MySQL : insert, then fetch created tuple
            val statement = connection.createStatement(insertSql(row))
            setStatementParams(row, table, statement)
            statement.execute().awaitSingle()
            fetchLastInserted(connection, row, table)
        } else {
            // other DB types have RETURNING style features
            val statement = connection.createStatement(insertSql(row, true))
            setStatementParams(row, table, statement)
            statement.execute().asFlow()
                .mapNotNull { r ->
                    r.map { row, _ ->
                        (table.table as AbstractTable<T>).toField(
                            tables.allColumns,
                            tables.allTables,
                            tables.dbType,
                        ).builder.invoke(row.toRow())
                    }.awaitFirstOrNull()
                }
                .first()
        }

    protected fun <T : Any> insertAndReturnProtected(rows: Array<T>): Flow<T> {
        require(rows.isNotEmpty()) { "rows must contain at least one element" }
        val table = tables.getTable(rows[0]::class)
        return flow {
            val r2dbcConnection = getR2dbcConnection(connectionFactory)
            try {
                emitAll(rows.asFlow()
                    .map { row -> executeInsertAndReturn(r2dbcConnection.connection, row, table) }
                )
            } finally {
                r2dbcConnection.apply {
                    if (!inTransaction) {
                        connection.close().awaitFirstOrNull()
                    }
                }
            }
        }
    }

    private fun <T : Any> setStatementParams(row: T, table: KotysaTable<T>, statement: Statement) {
        table.columns
            // do nothing for null values with default or Serial type
            .filterNot { column ->
                column.entityGetter(row) == null
                        && (column.defaultValue != null
                        || column.isAutoIncrement
                        || SqlType.SERIAL == column.sqlType
                        || SqlType.BIGSERIAL == column.sqlType)
            }
            .forEachIndexed { index, column ->
                val value = column.entityGetter(row)
                if (value == null) {
                    statement.bindNull(
                        index,
                        (column.entityGetter.toCallable().returnType.classifier as KClass<*>).toDbClass().java
                    )
                } else {
                    val dbValue = tables.getDbValue(value)!!
                    when (this.tables.dbType) {
                        DbType.H2 -> statement.bind("$${index + 1}", dbValue)
                        else -> statement.bind(index, dbValue)
                    }
                }
            }
    }

    private suspend fun <T : Any> fetchLastInserted(connection: Connection, row: T, table: KotysaTable<T>): T {
        val pkColumns = table.primaryKey.columns
        val statement = connection.createStatement(lastInsertedSql(row))
        val pkFirstColumn = pkColumns.elementAt(0)
        if (
            pkColumns.size != 1 ||
            !pkFirstColumn.isAutoIncrement ||
            pkFirstColumn.entityGetter(row) != null
        ) {
            // bind all PK values
            pkColumns
                .map { column -> tables.getDbValue(column.entityGetter(row)) }
                .forEachIndexed { index, dbValue -> statement.bind(index, dbValue!!) }
        }

        return statement.execute().awaitSingle()
            .map { r, _ ->
                (table.table as AbstractTable<T>).toField(
                    tables.allColumns,
                    tables.allTables,
                    tables.dbType,
                ).builder.invoke(r.toRow())
            }.awaitSingle()
    }

    protected suspend fun <T : Any> createTableProtected(table: Table<T>) {
        createTable(table, false)
    }

    protected suspend fun <T : Any> createTableIfNotExistsProtected(table: Table<T>) {
        createTable(table, true)
    }

    private suspend fun <T : Any> createTable(table: Table<T>, ifNotExists: Boolean) {
        val createTableResult = createTableSql(table, ifNotExists)

        getR2dbcConnection(connectionFactory).execute { connection ->
            connection.createStatement(createTableResult.sql)
                .execute().awaitLast()
            // 2) loop to execute create indexes
            createTableResult.createIndexes.forEach { createIndexResult ->
                try {
                    connection.createStatement(createIndexResult.sql)
                        .execute().awaitLast()
                } catch (se: R2dbcBadGrammarException) {
                    // if not exists : accept Index already exists error
                    if (!ifNotExists || se.message?.contains(createIndexResult.name, true) != true) {
                        throw se
                    }
                }
            }
        }
    }

    protected fun <T : Any> deleteFromProtected(table: Table<T>)
    : CoroutinesSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        SqlClientDeleteR2dbc.FirstDelete(connectionFactory, tables, table)

    protected fun <T : Any> updateProtected(table: Table<T>): CoroutinesSqlClientDeleteOrUpdate.Update<T> =
        SqlClientUpdateR2dbc.FirstUpdate(connectionFactory, tables, table)

    protected fun <T : Any, U : Any> selectProtected(column: Column<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).select(column)

    protected fun <T : Any> selectProtected(table: Table<T>): CoroutinesSqlClientSelect.FirstSelect<T> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).select(table)

    protected fun <T : Any> selectAndBuildProtected(dsl: (ValueProvider) -> T): CoroutinesSqlClientSelect.Fromable<T> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectAndBuild(dsl)

    protected fun selectCountProtected(): CoroutinesSqlClientSelect.Fromable<Long> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectCount<Any>(null)

    protected fun <T : Any> selectCountProtected(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectCount(column)

    protected fun <T : Any, U : Any> selectDistinctProtected(column: Column<T, U>)
    : CoroutinesSqlClientSelect.FirstSelect<U> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectDistinct(column)

    protected fun <T : Any, U : Any> selectMinProtected(column: MinMaxColumn<T, U>)
    : CoroutinesSqlClientSelect.FirstSelect<U> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectMin(column)

    protected fun <T : Any, U : Any> selectMaxProtected(column: MinMaxColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectMax(column)

    protected fun <T : Any, U : Any> selectAvgProtected(column: NumericColumn<T, U>)
    : CoroutinesSqlClientSelect.FirstSelect<BigDecimal> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectAvg(column)

    protected fun <T : Any> selectSumProtected(column: IntColumn<T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectSum(column)

    protected fun <T : Any> selectProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): CoroutinesSqlClientSelect.FirstSelect<T> = SqlClientSelectR2dbc.Selectable(connectionFactory, tables).select(dsl)

    protected fun <T : Any> selectCaseWhenExistsProtected(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): CoroutinesSqlClientSelect.SelectCaseWhenExistsFirst<T> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectCaseWhenExists(dsl)

    protected fun <T : Any> selectStarFromProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): CoroutinesSqlClientSelect.From<T> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectStarFromSubQuery(dsl)

    protected suspend fun <T> transactionalProtected(block: suspend (R2dbcTransaction) -> T): T? {
        // reuse currentTransaction if any, else create new transaction from new established connection
        val currentTransaction = coroutineContext[R2dbcTransactionImpl]
        val isOrigin = currentTransaction == null
        var context = coroutineContext
        val transaction = currentTransaction
        // if new transaction : add it to coroutineContext
            ?: R2dbcTransactionImpl(connectionFactory.create().awaitSingle()).apply { context += this }
        var throwable: Throwable? = null

        // use transaction's Connection
        return with(transaction.connection) {
            setAutoCommit(false).awaitFirstOrNull() // default true

            try {
                val result = try {
                    withContext(context) {
                        block.invoke(transaction)
                    }
                } catch (ex: SQLException) { // An expected checked Exception in JDBC
                    throwable = ex
                    throw ex
                } catch (ex: RuntimeException) {
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
                        if (transaction.isRollbackOnly() || throwable != null) {
                            rollbackTransaction().awaitFirstOrNull()
                        } else {
                            commitTransaction().awaitFirstOrNull()
                        }
                    } finally {
                        try {
                            transaction.setCompleted()
                            close().awaitFirstOrNull()
                        } catch (_: Throwable) {
                            // ignore exception of connection.close()
                        }
                    }
                }
            }
        }
    }
}

internal class H2SqlClientR2dbc internal constructor(
    connectionFactory: ConnectionFactory,
    tables: H2Tables,
) : SqlClientR2dbc(connectionFactory, tables), H2R2dbcSqlClient {
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
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override suspend fun <U> transactional(block: suspend (R2dbcTransaction) -> U) = transactionalProtected(block)
}

internal class MysqlSqlClientR2dbc internal constructor(
    connectionFactory: ConnectionFactory,
    tables: MysqlTables,
) : SqlClientR2dbc(connectionFactory, tables), MysqlR2dbcSqlClient {
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
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override suspend fun <U> transactional(block: suspend (R2dbcTransaction) -> U) = transactionalProtected(block)
}

internal class PostgresqlSqlClientR2dbc internal constructor(
    connectionFactory: ConnectionFactory,
    tables: PostgresqlTables,
) : SqlClientR2dbc(connectionFactory, tables), PostgresqlR2dbcSqlClient {
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
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override suspend fun <U> transactional(block: suspend (R2dbcTransaction) -> U) = transactionalProtected(block)
}

internal class MssqlSqlClientR2dbc internal constructor(
    connectionFactory: ConnectionFactory,
    tables: MssqlTables,
) : SqlClientR2dbc(connectionFactory, tables), MssqlR2dbcSqlClient {
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
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override suspend fun <U> transactional(block: suspend (R2dbcTransaction) -> U) = transactionalProtected(block)
}

internal class MariadbSqlClientR2dbc internal constructor(
    connectionFactory: ConnectionFactory,
    tables: MariadbTables,
) : SqlClientR2dbc(connectionFactory, tables), MariadbR2dbcSqlClient {
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
    override fun <T : Any> selectSum(column: IntColumn<T>) = selectSumProtected(column)
    override fun <T : Any> select(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) = selectProtected(dsl)

    override fun <T : Any> selectCaseWhenExists(dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>) =
        selectCaseWhenExistsProtected(dsl)

    override fun <T : Any> selectStarFrom(dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>) =
        selectStarFromProtected(dsl)

    override suspend fun <U> transactional(block: suspend (R2dbcTransaction) -> U) = transactionalProtected(block)
}

internal suspend fun getR2dbcConnection(connectionFactory: ConnectionFactory): R2dbcConnection {
    // reuse currentTransaction's connection if any, else establish a new connection
    val transaction = coroutineContext[R2dbcTransactionImpl]
    val connection = transaction?.connection ?: connectionFactory.create().awaitSingle()

    return R2dbcConnection(connection, transaction != null)
}

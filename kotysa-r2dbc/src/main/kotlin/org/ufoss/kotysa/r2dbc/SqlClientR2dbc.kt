/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.Statement
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.*
import kotlinx.coroutines.withContext
import org.ufoss.kotysa.*
import org.ufoss.kotysa.core.r2dbc.toRow
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.transaction.CoroutinesTransactionalOp
import java.lang.reflect.UndeclaredThrowableException
import java.math.BigDecimal
import java.sql.SQLException
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.coroutines.coroutineContext
import kotlin.reflect.KClass

public interface R2dbcSqlClient : CoroutinesSqlClient, CoroutinesTransactionalOp<R2dbcTransaction>

/**
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
internal class SqlClientR2dbc(
    private val connectionFactory: ConnectionFactory,
    override val tables: Tables
) : R2dbcSqlClient, DefaultSqlClient {

    override val module = Module.R2DBC

    override suspend fun <T : Any> insert(row: T) {
        val table = tables.getTable(row::class)

        getR2dbcConnection(connectionFactory).execute { connection ->
            val statement = connection.createStatement(insertSql(row))
            setStatementParams(row, table, statement)

            statement.execute().awaitSingle().rowsUpdated.awaitSingle()
        }
    }

    override suspend fun <T : Any> insert(vararg rows: T) {
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

    override suspend fun <T : Any> insertAndReturn(row: T): T {
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
                        ).builder.invoke(row.toRow())
                    }.awaitFirstOrNull()
                }
                .first()
        }

    override fun <T : Any> insertAndReturn(vararg rows: T): Flow<T> {
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
        @Suppress("UNCHECKED_CAST")
        val pkColumns = table.primaryKey.columns as List<DbColumn<T, *>>
        val statement = connection.createStatement(lastInsertedSql(row))

        if (
            pkColumns.size != 1 ||
            !pkColumns[0].isAutoIncrement ||
            pkColumns[0].entityGetter(row) != null
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
                    tables.allTables
                ).builder.invoke(r.toRow())
            }.awaitSingle()
    }

    override suspend fun <T : Any> createTable(table: Table<T>) {
        createTable(table, false)
    }

    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) {
        createTable(table, true)
    }

    private suspend fun <T : Any> createTable(table: Table<T>, ifNotExists: Boolean) {
        val createTableSql = createTableSql(table, ifNotExists)

        getR2dbcConnection(connectionFactory).execute { connection ->
            connection.createStatement(createTableSql)
                .execute().awaitLast()
        }
    }

    override fun <T : Any> deleteFrom(table: Table<T>): CoroutinesSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        SqlClientDeleteR2dbc.FirstDelete(connectionFactory, tables, table)

    override fun <T : Any> update(table: Table<T>): CoroutinesSqlClientDeleteOrUpdate.Update<T> =
        SqlClientUpdateR2dbc.FirstUpdate(connectionFactory, tables, table)

    override fun <T : Any, U : Any> select(column: Column<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).select(column)

    override fun <T : Any> select(table: Table<T>): CoroutinesSqlClientSelect.FirstSelect<T> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).select(table)

    override fun <T : Any> select(dsl: (ValueProvider) -> T): CoroutinesSqlClientSelect.Fromable<T> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).select(dsl)

    override fun selectCount(): CoroutinesSqlClientSelect.Fromable<Long> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectCount<Any>(null)

    override fun <T : Any> selectCount(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectCount(column)

    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectDistinct(column)

    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectMin(column)

    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectMax(column)

    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<BigDecimal> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectAvg(column)

    override fun <T : Any> selectSum(column: IntColumn<T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
        SqlClientSelectR2dbc.Selectable(connectionFactory, tables).selectSum(column)

    override suspend fun <T> execute(block: suspend (R2dbcTransaction) -> T): T? {
        // reuse currentTransaction if any, else create new transaction from new established connection
        val currentTransaction = coroutineContext[R2dbcTransaction]
        val isOrigin = currentTransaction == null
        var context = coroutineContext
        val transaction = currentTransaction
        // if new transaction : add it to coroutineContext
            ?: R2dbcTransaction(connectionFactory.create().awaitSingle()).apply { context += this }
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

internal fun KClass<*>.toDbClass() =
    when (this.qualifiedName) {
        "kotlinx.datetime.LocalDate" -> LocalDate::class
        "kotlinx.datetime.LocalDateTime" -> LocalDateTime::class
        else -> this
    }

internal suspend fun getR2dbcConnection(connectionFactory: ConnectionFactory): R2dbcConnection {
    // reuse currentTransaction's connection if any, else establish a new connection
    val transaction = coroutineContext[R2dbcTransaction]
    val connection = transaction?.connection ?: connectionFactory.create().awaitSingle()

    return R2dbcConnection(connection, transaction != null)
}

/**
 * Create a [CoroutinesSqlClient] from a R2DBC [Connection] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public fun ConnectionFactory.sqlClient(tables: Tables): R2dbcSqlClient = SqlClientR2dbc(this, tables)

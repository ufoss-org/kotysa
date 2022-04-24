/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.*
import org.ufoss.kotysa.core.jdbc.toRow
import org.ufoss.kotysa.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.transaction.TransactionalOp
import java.lang.reflect.UndeclaredThrowableException
import java.math.BigDecimal
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import javax.sql.DataSource

public interface JdbcSqlClient : SqlClient, TransactionalOp<JdbcTransaction>

/**
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
internal class SqlClientJdbc(
    private val dataSource: DataSource,
    override val tables: Tables,
) : JdbcSqlClient, DefaultSqlClient {
    private val threadLocal = ThreadLocal<JdbcTransaction>()

    override val module = Module.JDBC
    private val currentTransaction: JdbcTransaction?
        get() = threadLocal.get()

    override fun <T : Any> insert(row: T) {
        val table = tables.getTable(row::class)

        getJdbcConnection().execute { connection ->
            executeInsert(connection, row, table)
        }
    }

    private fun <T : Any> executeInsert(connection: Connection, row: T, table: KotysaTable<T>) {
        val statement = connection.prepareStatement(insertSql(row))
        setStatementParams(row, table, statement)
        statement.execute()
    }

    override fun <T : Any> insert(vararg rows: T) {
        require(rows.isNotEmpty()) { "rows must contain at least one element" }
        val table = tables.getTable(rows[0]::class)

        getJdbcConnection().execute { connection ->
            rows.forEach { row -> executeInsert(connection, row, table) }
        }
    }

    override fun <T : Any> insertAndReturn(row: T): T {
        val table = tables.getTable(row::class)

        return getJdbcConnection().execute { connection ->
            executeInsertAndReturn(connection, row, table)
        }
    }

    private fun <T : Any> executeInsertAndReturn(connection: Connection, row: T, table: KotysaTable<T>) =
        if (tables.dbType == DbType.MYSQL) {
            // For MySQL : insert, then fetch created tuple
            val statement = connection.prepareStatement(insertSql(row))
            setStatementParams(row, table, statement)
            statement.execute()
            fetchLastInserted(connection, row, table)
        } else {
            // other DB types have RETURNING style features
            val statement = connection.prepareStatement(insertSql(row, true))
            setStatementParams(row, table, statement)
            val rs = statement.executeQuery()
            rs.next()
            (table.table as AbstractTable<T>).toField(
                tables.allColumns,
                tables.allTables,
                tables.dbType,
            ).builder(rs.toRow())
        }

    override fun <T : Any> insertAndReturn(vararg rows: T): List<T> {
        val table = tables.getTable(rows[0]::class)

        return getJdbcConnection().execute { connection ->
            rows.map { row -> executeInsertAndReturn(connection, row, table) }
        }
    }

    private fun <T : Any> setStatementParams(row: T, table: KotysaTable<T>, statement: PreparedStatement) {
        table.columns
            // do nothing for null values with default or Serial type
            .filterNot { column ->
                column.entityGetter(row) == null
                        && (column.defaultValue != null
                        || column.isAutoIncrement
                        || SqlType.SERIAL == column.sqlType
                        || SqlType.BIGSERIAL == column.sqlType)
            }
            .map { column -> tables.getDbValue(column.entityGetter(row)) }
            .forEachIndexed { index, dbValue -> statement.setObject(index + 1, dbValue) }
    }

    private fun <T : Any> fetchLastInserted(connection: Connection, row: T, table: KotysaTable<T>): T {
        @Suppress("UNCHECKED_CAST")
        val pkColumns = table.primaryKey.columns as List<DbColumn<T, *>>
        val statement = connection.prepareStatement(lastInsertedSql(row))

        if (
            pkColumns.size != 1 ||
            !pkColumns[0].isAutoIncrement ||
            pkColumns[0].entityGetter(row) != null
        ) {
            // bind all PK values
            pkColumns
                .map { column -> tables.getDbValue(column.entityGetter(row)) }
                .forEachIndexed { index, dbValue -> statement.setObject(index + 1, dbValue) }
        }

        val rs = statement.executeQuery()
        rs.next()
        return (table.table as AbstractTable<T>).toField(
            tables.allColumns,
            tables.allTables,
            tables.dbType,
        ).builder.invoke(rs.toRow())
    }

    override fun <T : Any> createTable(table: Table<T>) {
        createTable(table, false)
    }

    override fun <T : Any> createTableIfNotExists(table: Table<T>) {
        createTable(table, true)
    }

    private fun <T : Any> createTable(table: Table<T>, ifNotExists: Boolean) {
        val createTableSql = createTableSql(table, ifNotExists)
        getJdbcConnection().execute { connection ->
            connection.prepareStatement(createTableSql)
                .execute()
        }
    }

    override fun <T : Any> deleteFrom(table: Table<T>): SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        SqlClientDeleteJdbc.FirstDelete(getJdbcConnection(), tables, table)

    override fun <T : Any> update(table: Table<T>): SqlClientDeleteOrUpdate.Update<T> =
        SqlClientUpdateJdbc.FirstUpdate(getJdbcConnection(), tables, table)

    override fun <T : Any, U : Any> select(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).select(column)

    override fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).select(table)

    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectAndBuild(dsl)

    override fun selectCount(): SqlClientSelect.Fromable<Long> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectCount<Any>(null)

    override fun <T : Any> selectCount(column: Column<*, T>): SqlClientSelect.FirstSelect<Long> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectCount(column)

    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectDistinct(column)

    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectMin(column)

    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectMax(column)

    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): SqlClientSelect.FirstSelect<BigDecimal> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectAvg(column)

    override fun <T : Any> selectSum(column: IntColumn<T>): SqlClientSelect.FirstSelect<Long> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectSum(column)

    override fun <T : Any> select(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.FirstSelect<T> = SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).select(dsl)

    override fun <T : Any> selectCaseWhenExists(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.SelectCaseWhenExistsFirst<T> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectCaseWhenExists(dsl)

    override fun <T : Any> selectStarFrom(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.From<T> = SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectStarFromSubQuery(dsl)

    override fun <T> transactional(block: (JdbcTransaction) -> T): T? {
        // reuse currentTransaction if any, else create new transaction from new established connection
        val isOrigin = currentTransaction == null
        val transaction = currentTransaction ?: JdbcTransaction(dataSource.connection).apply { threadLocal.set(this) }
        var throwable: Throwable? = null

        // use transaction's Connection
        return with(transaction.connection) {
            autoCommit = false // default true

            try {
                val result = try {
                    block.invoke(transaction)
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
                            rollback()
                        } else {
                            commit()
                        }
                    } finally {
                        try {
                            transaction.setCompleted()
                            if (!isClosed) {
                                close()
                            }
                        } catch (_: Throwable) {
                            // ignore exception of connection.close()
                        } finally {
                            threadLocal.remove()
                        }
                    }
                }
            }
        }
    }

    private fun getJdbcConnection(): JdbcConnection {
        // reuse currentTransaction's connection if any, else establish a new connection
        val transaction = currentTransaction
        val connection = transaction?.connection ?: dataSource.connection

        return JdbcConnection(connection, transaction != null)
    }
}

/**
 * Create a [JdbcSqlClient] from a JDBC [DataSource] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
public fun DataSource.sqlClient(tables: Tables): JdbcSqlClient = SqlClientJdbc(this, tables)

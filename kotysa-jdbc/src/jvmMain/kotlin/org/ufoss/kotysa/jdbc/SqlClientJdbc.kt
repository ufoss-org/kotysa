/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.TsvectorColumn
import org.ufoss.kotysa.core.jdbc.toRow
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.jdbc.transaction.JdbcTransactionImpl
import org.ufoss.kotysa.postgresql.Tsquery
import java.lang.reflect.UndeclaredThrowableException
import java.math.BigDecimal
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

/**
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
internal sealed class SqlClientJdbc(
    private val dataSource: DataSource,
    override val tables: Tables,
) : DefaultSqlClient {
    private val threadLocal = ThreadLocal<JdbcTransactionImpl>()

    override val module = Module.JDBC
    private val currentTransaction: JdbcTransactionImpl?
        get() = threadLocal.get()

    protected fun <T : Any> insertProtected(row: T) {
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

    protected fun <T : Any> insertProtected(rows: Array<T>) {
        require(rows.isNotEmpty()) { "rows must contain at least one element" }
        val table = tables.getTable(rows[0]::class)

        getJdbcConnection().execute { connection ->
            rows.forEach { row -> executeInsert(connection, row, table) }
        }
    }

    protected fun <T : Any> insertAndReturnProtected(row: T): T {
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
            val index = setStatementParams(row, table, statement)
            setOracleReturnParameter(table, index, statement)
            val rs = statement.executeQuery()
            rs.next()
            (table.table as AbstractTable<T>).toField(
                tables.allColumns,
                tables.allTables,
                tables.dbType,
            ).builder(rs.toRow())
        }

    protected fun <T : Any> insertAndReturnProtected(rows: Array<T>): List<T> {
        val table = tables.getTable(rows[0]::class)

        return getJdbcConnection().execute { connection ->
            rows.map { row -> executeInsertAndReturn(connection, row, table) }
        }
    }

    private fun <T : Any> fetchLastInserted(connection: Connection, row: T, table: KotysaTable<T>): T {
        val pkColumns = table.primaryKey.columns
        val statement = connection.prepareStatement(lastInsertedSql(row))
        val pkFirstColumn = pkColumns.elementAt(0)
        if (
            pkColumns.size != 1 ||
            !pkFirstColumn.isAutoIncrement ||
            pkFirstColumn.entityGetter(row) != null
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

    protected fun <T : Any> createTableProtected(table: Table<T>) {
        createTable(table, false)
    }

    protected fun <T : Any> createTableIfNotExistsProtected(table: Table<T>) {
        createTable(table, true)
    }

    private fun <T : Any> createTable(table: Table<T>, ifNotExists: Boolean) {
        val createTableResult = createTableSql(table, ifNotExists)
        getJdbcConnection().execute { connection ->
            // 1) execute create table
            connection.createStatement()
                .execute(createTableResult.sql)
            // 2) loop to execute create indexes
            createTableResult.createIndexes.forEach { createIndexResult ->
                try {
                    connection.createStatement()
                        .execute(createIndexResult.sql)
                } catch (se: SQLException) {
                    // if not exists : accept Index already exists error
                    if (!ifNotExists
                        || (se.message?.contains(createIndexResult.name, true) != true
                                // For Oracle, the name is not mentioned 'ORA-00955: name is already used by an existing object'
                                && se.message?.contains("ORA-00955", true) != true)
                    ) {
                        throw se
                    }
                }
            }
        }
    }

    protected fun <T : Any> deleteFromProtected(table: Table<T>): SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        SqlClientDeleteJdbc.FirstDelete(getJdbcConnection(), tables, table)

    protected fun <T : Any> updateProtected(table: Table<T>): SqlClientDeleteOrUpdate.Update<T> =
        SqlClientUpdateJdbc.FirstUpdate(getJdbcConnection(), tables, table)

    protected fun <T : Any, U : Any> selectProtected(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).select(column)

    protected fun <T : Any> selectProtected(table: Table<T>): SqlClientSelect.FirstSelect<T> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).select(table)

    protected fun <T : Any> selectAndBuildProtected(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectAndBuild(dsl)

    protected fun selectCountProtected(): SqlClientSelect.Fromable<Long> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectCount<Any>(null)

    protected fun <T : Any> selectCountProtected(column: Column<*, T>): SqlClientSelect.FirstSelect<Long> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectCount(column)

    protected fun <T : Any, U : Any> selectDistinctProtected(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectDistinct(column)

    protected fun <T : Any, U : Any> selectMinProtected(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectMin(column)

    protected fun <T : Any, U : Any> selectMaxProtected(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectMax(column)

    protected fun <T : Any, U : Any> selectAvgProtected(column: NumericColumn<T, U>): SqlClientSelect.FirstSelect<BigDecimal> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectAvg(column)

    protected fun <T : Any> selectSumProtected(column: IntColumn<T>): SqlClientSelect.FirstSelect<Long> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectSum(column)

    protected fun selectTsRankCdProtected(
        tsvectorColumn: TsvectorColumn<*>,
        tsquery: Tsquery,
    ): SqlClientSelect.FirstSelect<Float> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectTsRankCd(tsvectorColumn, tsquery)

    protected fun <T : Any> selectProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.FirstSelect<T> = SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).select(dsl)

    protected fun <T : Any> selectCaseWhenExistsProtected(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.SelectCaseWhenExistsFirst<T> =
        SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectCaseWhenExists(dsl)

    protected fun <T : Any> selectStarFromProtected(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.From<T> = SqlClientSelectJdbc.Selectable(getJdbcConnection(), tables).selectStarFromSubQuery(dsl)

    protected fun <T> transactionalProtected(block: (JdbcTransaction) -> T): T? {
        // reuse currentTransaction if any, else create new transaction from new established connection
        val isOrigin = currentTransaction == null
        var transaction = currentTransaction
        transaction = if (transaction != null && !transaction.isCompleted()) {
            transaction
        } else {
            JdbcTransactionImpl(dataSource.connection).apply { threadLocal.set(this) }
        }
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
        val connection = if (transaction != null && !transaction.isCompleted()) {
            transaction.connection
        } else {
            dataSource.connection
        }

        return JdbcConnection(connection, transaction != null)
    }
}

internal class H2SqlClientJdbc internal constructor(
    dataSource: DataSource,
    tables: H2Tables,
) : SqlClientJdbc(dataSource, tables), H2JdbcSqlClient {
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

    override fun <U> transactional(block: (JdbcTransaction) -> U) = transactionalProtected(block)
}

internal class MysqlSqlClientJdbc internal constructor(
    dataSource: DataSource,
    tables: MysqlTables,
) : SqlClientJdbc(dataSource, tables), MysqlJdbcSqlClient {
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

    override fun <U> transactional(block: (JdbcTransaction) -> U) = transactionalProtected(block)
}

internal class PostgresqlSqlClientJdbc internal constructor(
    dataSource: DataSource,
    tables: PostgresqlTables,
) : SqlClientJdbc(dataSource, tables), PostgresqlJdbcSqlClient {
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

    override fun <U> transactional(block: (JdbcTransaction) -> U) = transactionalProtected(block)
}

internal class MssqlSqlClientJdbc internal constructor(
    dataSource: DataSource,
    tables: MssqlTables,
) : SqlClientJdbc(dataSource, tables), MssqlJdbcSqlClient {
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

    override fun <U> transactional(block: (JdbcTransaction) -> U) = transactionalProtected(block)
}

internal class MariadbSqlClientJdbc internal constructor(
    dataSource: DataSource,
    tables: MariadbTables,
) : SqlClientJdbc(dataSource, tables), MariadbJdbcSqlClient {
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

    override fun <U> transactional(block: (JdbcTransaction) -> U) = transactionalProtected(block)
}

internal class OracleSqlClientJdbc internal constructor(
    dataSource: DataSource,
    tables: OracleTables,
) : SqlClientJdbc(dataSource, tables), OracleJdbcSqlClient {
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

    override fun <U> transactional(block: (JdbcTransaction) -> U) = transactionalProtected(block)
}

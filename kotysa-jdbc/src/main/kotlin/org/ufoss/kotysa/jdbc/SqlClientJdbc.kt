/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.*
import java.math.BigDecimal
import java.sql.Connection

/**
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
internal class SqlClientJdbc(
    private val connection: Connection,
    override val tables: Tables
) : SqlClient, DefaultSqlClient {

    override val module = Module.JDBC

    override fun <T : Any> insert(row: T) {
        val table = tables.getTable(row::class)

        val statement = connection.prepareStatement(insertSql(row))
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

        statement.execute()
    }

    override fun <T : Any> insert(vararg rows: T) {
        rows.forEach { row -> insert(row) }
    }

    override fun <T : Any> insertAndReturn(row: T): T {
        TODO("Not yet implemented")
    }

    override fun <T : Any> insertAndReturn(vararg rows: T): List<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any> createTable(table: Table<T>) {
        createTable(table, false)
    }

    override fun <T : Any> createTableIfNotExists(table: Table<T>) {
        createTable(table, true)
    }

    private fun <T : Any> createTable(table: Table<T>, ifNotExists: Boolean) {
        val createTableSql = createTableSql(table, ifNotExists)
        connection.prepareStatement(createTableSql)
            .execute()
    }

    override fun <T : Any> deleteFrom(table: Table<T>): SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        SqlClientDeleteJdbc.FirstDelete(connection, tables, table)

    override fun <T : Any> update(table: Table<T>): SqlClientDeleteOrUpdate.Update<T> =
        SqlClientUpdateJdbc.FirstUpdate(connection, tables, table)

    override fun <T : Any, U : Any> select(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(connection, tables).select(column)
    
    override fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T> =
        SqlClientSelectJdbc.Selectable(connection, tables).select(table)
    
    override fun <T : Any> select(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> =
        SqlClientSelectJdbc.Selectable(connection, tables).select(dsl)
    
    override fun selectCount(): SqlClientSelect.Fromable<Long> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectCount<Any>(null)
    
    override fun <T : Any> selectCount(column: Column<*, T>): SqlClientSelect.FirstSelect<Long> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectCount(column)
    
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectDistinct(column)
    
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectMin(column)
    
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectMax(column)
    
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): SqlClientSelect.FirstSelect<BigDecimal> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectAvg(column)
    
    override fun <T : Any> selectSum(column: IntColumn<T>): SqlClientSelect.FirstSelect<Long> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectSum(column)
}

/**
 * Create a [SqlClient] from a JDBC [Connection] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.jdbc.sample.UserRepositoryJdbc
 */
public fun Connection.sqlClient(tables: Tables): SqlClient = SqlClientJdbc(this, tables)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.ufoss.kotysa.*
import java.math.BigDecimal

/**
 * @sample org.ufoss.kotysa.spring.jdbc.sample.UserRepositorySpringJdbc
 */
internal class SqlClientSpringJdbc(
        private val client: JdbcOperations,
        override val tables: Tables
) : SqlClient, DefaultSqlClient {

    override val module = Module.SPRING_JDBC
    
    /**
     * Computed property : only created once on first call
     */
    private val namedParameterJdbcOperations: NamedParameterJdbcOperations by lazy {
        NamedParameterJdbcTemplate(client)
    }

    override fun <T : Any> insert(row: T) {
        val table = tables.getTable(row::class)

        val parameters = MapSqlParameterSource()
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
                .forEachIndexed { index, dbValue -> parameters.addValue("k$index", dbValue)  }

        namedParameterJdbcOperations.update(insertSql(row), parameters)
    }

    override fun <T : Any> insert(vararg rows: T) {
        rows.forEach { row -> insert(row) }
    }

    override fun <T : Any> createTable(table: Table<T>) {
        createTable(table, false)
    }

    override fun <T : Any> createTableIfNotExists(table: Table<T>) {
        createTable(table, true)
    }

    private fun <T : Any> createTable(table: Table<T>, ifNotExists: Boolean) {
        val createTableSql = createTableSql(table, ifNotExists)
        client.execute(createTableSql)
    }

    override fun <T : Any> deleteFrom(table: Table<T>): SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
            SqlClientDeleteSpringJdbc.FirstDelete(namedParameterJdbcOperations, tables, table)

    override fun <T : Any> update(table: Table<T>): SqlClientDeleteOrUpdate.Update<T> =
            SqlClientUpdateSpringJdbc.FirstUpdate(namedParameterJdbcOperations, tables, table)

    override fun <T : Any, U : Any> select(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
            SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).select(column)
    override fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T> =
            SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).select(table)
    override fun <T : Any> select(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> =
            SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).select(dsl)
    override fun selectCount(): SqlClientSelect.Fromable<Long> =
            SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectCount<Any>(null)
    override fun <T : Any> selectCount(column: Column<*, T>): SqlClientSelect.FirstSelect<Long> =
            SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectCount(column)
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
            SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectDistinct(column)
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
            SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectMin(column)
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
            SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectMax(column)
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): SqlClientSelect.FirstSelect<BigDecimal> =
            SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectAvg(column)
    override fun <T : Any> selectSum(column: IntColumn<T>): SqlClientSelect.FirstSelect<Long> =
            SqlClientSelectSpringJdbc.Selectable(namedParameterJdbcOperations, tables).selectSum(column)
}

/**
 * Create a [SqlClient] from a Spring [JdbcOperations] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.spring.jdbc.sample.UserRepositorySpringJdbc
 */
public fun JdbcOperations.sqlClient(tables: Tables): SqlClient = SqlClientSpringJdbc(this, tables)

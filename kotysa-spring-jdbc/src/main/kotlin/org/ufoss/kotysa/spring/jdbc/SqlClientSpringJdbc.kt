/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.ufoss.kotysa.*
import kotlin.reflect.KClass

/**
 * @sample org.ufoss.kotysa.spring.jdbc.sample.UserRepositorySpringJdbc
 */
internal class SqlClientSpringJdbc(
        private val client: JdbcOperations,
        override val tables: Tables
) : SqlClient, DefaultSqlClient {

    /**
     * Computed property : only created once on first call
     */
    private val namedParameterJdbcOperations: NamedParameterJdbcOperations by lazy {
        NamedParameterJdbcTemplate(client)
    }

    override fun <T : Any> select(resultClass: KClass<T>,
                                  dsl: (SelectDslApi.(ValueProvider) -> T)?): SqlClientSelect.Select<T> =
            SqlClientSelectSpringJdbc.Select(namedParameterJdbcOperations, tables, resultClass, dsl)

    override fun <T : Any> createTable(tableClass: KClass<T>) {
        val createTableSql = createTableSql(tableClass)
        return client.execute(createTableSql)
    }

    override fun <T : Any> deleteFromTable(tableClass: KClass<T>): SqlClientDeleteOrUpdate.DeleteOrUpdate<T> =
            SqlClientDeleteSpringJdbc.Delete(namedParameterJdbcOperations, tables, tableClass)

    override fun <T : Any> updateTable(tableClass: KClass<T>): SqlClientDeleteOrUpdate.Update<T> =
            SqlClientUpdateSpringJdbc.Update(namedParameterJdbcOperations, tables, tableClass)

    override fun <T : Any> insert(row: T) {
        val table = tables.getTable(row::class)

        val parameters = MapSqlParameterSource()
        table.columns.values
                // do nothing for null values with default or Serial type
                .filterNot { column ->
                    column.entityGetter(row) == null
                            && (column.defaultValue != null || SqlType.SERIAL == column.sqlType)
                }
                .map { column -> tables.getDbValue(column.entityGetter(row)) }
                .forEachIndexed { index, dbValue -> parameters.addValue("k$index", dbValue)  }

        namedParameterJdbcOperations.update(insertSql(row), parameters)
    }

    override fun <T : Any> insert(vararg rows: T) {
        checkRowsAreMapped(*rows)

        rows.forEach { row -> insert(row) }
    }

    override fun <T : Any> createTable(table: Table<T>) {
        TODO("Not yet implemented")
    }

    override fun <T : Any> deleteFrom(table: Table<T>): SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any> update(table: Table<T>): SqlClientDeleteOrUpdate.Update<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any, U : Any> select(column: ColumnNotNull<T, U>): SqlClientSelect.FirstSelect<U> {
        TODO("Not yet implemented")
    }

    override fun <T : Any, U : Any> select(column: ColumnNullable<T, U>): SqlClientSelect.FirstSelect<U?> {
        TODO("Not yet implemented")
    }

    override fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any> select(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any> selectCount(column: Column<*, T>): SqlClientSelect.FirstSelect<Int> {
        TODO("Not yet implemented")
    }

    override fun <T : Any> selectFrom(table: Table<T>): SqlClientSelect.From<T, T> {
        TODO("Not yet implemented")
    }
}

/**
 * Create a [SqlClient] from a Spring [JdbcOperations] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.spring.jdbc.sample.UserRepositorySpringJdbc
 */
public fun JdbcOperations.sqlClient(tables: Tables): SqlClient = SqlClientSpringJdbc(this, tables)

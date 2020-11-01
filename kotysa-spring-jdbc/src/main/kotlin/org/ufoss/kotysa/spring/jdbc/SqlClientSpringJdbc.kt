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
) : BlockingSqlClient(), DefaultSqlClient {

    /**
     * Computed property : only created once on first call
     */
    private val namedParameterJdbcOperations: NamedParameterJdbcOperations by lazy {
        NamedParameterJdbcTemplate(client)
    }

    override fun <T : Any> select(resultClass: KClass<T>,
                                  dsl: (SelectDslApi.(ValueProvider) -> T)?): BlockingSqlClientSelect.Select<T> =
            SqlClientSelectSpringJdbc.Select(namedParameterJdbcOperations, tables, resultClass, dsl)

    override fun <T : Any> createTable(tableClass: KClass<T>) {
        val createTableSql = createTableSql(tableClass)
        return client.execute(createTableSql)
    }

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

    override fun insert(vararg rows: Any) {
        checkRowsAreMapped(*rows)

        rows.forEach { row -> insert(row) }
    }

    override fun <T : Any> deleteFromTable(tableClass: KClass<T>): BlockingSqlClientDeleteOrUpdate.DeleteOrUpdate<T> =
            SqlClientDeleteSpringJdbc.Delete(namedParameterJdbcOperations, tables, tableClass)

    override fun <T : Any> updateTable(tableClass: KClass<T>): BlockingSqlClientDeleteOrUpdate.Update<T> =
            SqlClientUpdateSpringJdbc.Update(namedParameterJdbcOperations, tables, tableClass)
}

/**
 * Create a [BlockingSqlClient] from a Spring [JdbcOperations] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.spring.jdbc.sample.UserRepositorySpringJdbc
 */
public fun JdbcOperations.sqlClient(tables: Tables): BlockingSqlClient = SqlClientSpringJdbc(this, tables)

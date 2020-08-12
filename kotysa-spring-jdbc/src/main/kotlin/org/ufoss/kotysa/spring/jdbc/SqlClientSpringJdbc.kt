/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.*
import kotlin.reflect.KClass

internal class SqlClientSpringJdbc(
        private val client: JdbcTemplate,
        override val tables: Tables
) : BlockingSqlClient(), DefaultSqlClient {

    override fun <T : Any> select(resultClass: KClass<T>,
                                  dsl: (SelectDslApi.(ValueProvider) -> T)?): BlockingSqlClientSelect.Select<T> =
            SqlClientSelectSpringJdbc.Select(client, tables, resultClass, dsl)

    override fun <T : Any> createTable(tableClass: KClass<T>) {
        val createTableSql = createTableSql(tableClass)
        return client.execute(createTableSql)
    }

    override fun <T : Any> insert(row: T) {
        val table = tables.getTable(row::class)
        client.update(insertSql(row),
                *table.columns.values
                        // do nothing for null values with default or Serial type
                        .filterNot { column ->
                            column.entityGetter(row) == null
                                    && (column.defaultValue != null || SqlType.SERIAL == column.sqlType)
                        }
                        .map { column -> column.entityGetter(row) }
                        .toTypedArray()
        )
    }

    override fun insert(vararg rows: Any) {
        checkRowsAreMapped(*rows)

        rows.forEach { row -> insert(row) }
    }

    override fun <T : Any> deleteFromTable(tableClass: KClass<T>): BlockingSqlClientDeleteOrUpdate.DeleteOrUpdate<T> =
            SqlClientDeleteSpringJdbc.Delete(client, tables, tableClass)

    override fun <T : Any> updateTable(tableClass: KClass<T>): BlockingSqlClientDeleteOrUpdate.Update<T> =
            SqlClientUpdateSpringJdbc.Update(client, tables, tableClass)
}

/**
 * Create a [BlockingSqlClient] from a Spring [JdbcTemplate] with [Tables] mapping
 */
public fun JdbcTemplate.sqlClient(tables: Tables): BlockingSqlClient = SqlClientSpringJdbc(this, tables)

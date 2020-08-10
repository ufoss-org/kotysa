/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.DefaultSqlClient
import org.ufoss.kotysa.SqlType
import org.ufoss.kotysa.getTable
import org.ufoss.kotysa.toCallable
import kotlin.reflect.KClass

/**
 * see [spring-data-r2dbc doc](https://docs.spring.io/spring-data/r2dbc/docs/1.0.x/reference/html/#reference)
 */
internal interface AbstractSqlClientR2dbc : DefaultSqlClient {

    val client: DatabaseClient

    fun <T : Any> executeCreateTable(tableClass: KClass<T>) =
            client.sql(createTableSql(tableClass))

    fun <T : Any> executeInsert(row: T): DatabaseClient.GenericExecuteSpec {
        var executeSpec = client.sql(insertSql(row))
        val table = tables.getTable(row::class)
        var index = 0
        table.columns.values.forEach { column ->
            val value = column.entityGetter(row)
            executeSpec = if (value == null) {
                // do nothing for null values with default or Serial type
                if (column.defaultValue != null || SqlType.SERIAL == column.sqlType) {
                    executeSpec
                } else {
                    executeSpec.bindNull(index++, (column.entityGetter.toCallable().returnType.classifier as KClass<*>).java)
                }
            } else {
                executeSpec.bind(index++, value)
            }
        }
        return executeSpec
    }
}

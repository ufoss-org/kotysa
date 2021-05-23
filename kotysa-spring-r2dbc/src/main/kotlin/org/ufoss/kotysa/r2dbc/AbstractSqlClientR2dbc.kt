/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.KClass

/**
 * see [spring-data-r2dbc doc](https://docs.spring.io/spring-data/r2dbc/docs/1.0.x/reference/html/#reference)
 */
internal interface AbstractSqlClientR2dbc : DefaultSqlClient {

    val client: DatabaseClient

    fun <T : Any> executeCreateTable(table: Table<T>, ifNotExists: Boolean): DatabaseClient.GenericExecuteSpec =
            client.sql(createTableSql(table, ifNotExists))

    fun <T : Any> executeInsert(row: T): DatabaseClient.GenericExecuteSpec {
        val table = tables.getTable(row::class)
        var index = 0
        return table.columns
                .fold(client.sql(insertSql(row))) { execSpec, column ->
                    val value = column.entityGetter(row)
                    if (value == null) {
                        // do nothing for null values with default or Serial type
                        if (column.defaultValue != null
                                || column.isAutoIncrement
                                || SqlType.SERIAL == column.sqlType
                                || SqlType.BIGSERIAL == column.sqlType) {
                            execSpec
                        } else {
                            execSpec.bindNull("k${index++}",
                                    (column.entityGetter.toCallable().returnType.classifier as KClass<*>).toDbClass().java)
                        }
                    } else {
                        execSpec.bind("k${index++}", tables.getDbValue(value)!!)
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

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import reactor.core.publisher.Mono
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

    fun <T : Any> executeInsert(row: T): DatabaseClient.GenericExecuteSpec =
        insertExecuteSpec(row, tables.getTable(row::class), insertSql(row))

    fun <T : Any> executeInsertAndReturn(row: T): Mono<T> {
        val table = tables.getTable(row::class)
        val executeSpec = insertExecuteSpec(row, table, insertSql(row, true))

        return if (tables.dbType == DbType.MYSQL) {
            // For MySQL : insert, then fetch created tuple
            executeSpec
                .then()
                .then(fetchLastInserted(row, table))
        } else {
            // other DB types have RETURNING style features
            executeSpec
                .map { r ->
                    (table.table as AbstractTable<T>).toField(
                        tables.allColumns,
                        tables.allTables
                    ).builder.invoke(r.toRow())
                }.one()
        }
    }

    private fun <T : Any> insertExecuteSpec(
        row: T,
        table: KotysaTable<T>,
        sql: String,
    ): DatabaseClient.GenericExecuteSpec {
        var index = 0

        return table.columns
            .fold(client.sql(sql)) { execSpec, column ->
                val value = column.entityGetter(row)
                if (value == null) {
                    // do nothing for null values with default or Serial type
                    if (column.defaultValue != null
                        || column.isAutoIncrement
                        || SqlType.SERIAL == column.sqlType
                        || SqlType.BIGSERIAL == column.sqlType
                    ) {
                        execSpec
                    } else {
                        execSpec.bindNull(
                            "k${index++}",
                            (column.entityGetter.toCallable().returnType.classifier as KClass<*>).toDbClass().java
                        )
                    }
                } else {
                    execSpec.bind("k${index++}", tables.getDbValue(value)!!)
                }
            }
    }

    private fun <T : Any> fetchLastInserted(row: T, table: KotysaTable<T>): Mono<T> {
        @Suppress("UNCHECKED_CAST")
        val pkColumns = table.primaryKey.columns as List<DbColumn<T, *>>

        val executeSpec = if (pkColumns.size == 1 && pkColumns[0].isAutoIncrement) {
            client.sql(lastInsertedSql(row))
        } else {
            // bind all PK values
            pkColumns
                .foldIndexed(client.sql(lastInsertedSql(row))) { index, execSpec, column ->
                    val value = column.entityGetter(row)
                    execSpec.bind("k${index}", tables.getDbValue(value)!!)
                }
        }

        return executeSpec
            .map { r ->
                (table.table as AbstractTable<T>).toField(
                    tables.allColumns,
                    tables.allTables
                ).builder.invoke(r.toRow())
            }.one()
    }
}

internal fun KClass<*>.toDbClass() =
    when (this.qualifiedName) {
        "kotlinx.datetime.LocalDate" -> LocalDate::class
        "kotlinx.datetime.LocalDateTime" -> LocalDateTime::class
        else -> this
    }

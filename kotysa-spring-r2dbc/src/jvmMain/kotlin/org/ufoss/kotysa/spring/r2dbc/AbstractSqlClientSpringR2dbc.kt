/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.dao.NonTransientDataAccessException
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import org.ufoss.kotysa.core.r2dbc.toRow
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorResume
import kotlin.reflect.KClass

/**
 * see [spring-data-r2dbc doc](https://docs.spring.io/spring-data/r2dbc/docs/1.4.x/reference/html/#reference)
 */
internal interface AbstractSqlClientSpringR2dbc : DefaultSqlClient {

    val client: DatabaseClient

    fun <T : Any> executeInsert(row: T): DatabaseClient.GenericExecuteSpec =
        insertExecuteSpec(row, tables.getTable(row::class), insertSql(row))

    fun <T : Any> executeInsertAndReturn(row: T): Mono<T> {
        val table = tables.getTable(row::class)
        var executeSpec = insertExecuteSpec(row, table, insertSql(row, true))

        return if (tables.dbType == DbType.MYSQL) {
            // For MySQL : insert, then fetch created tuple
            executeSpec
                .then()
                .then(fetchLastInserted(row, table))
        } else {
            // other DB types have RETURNING style features
            executeSpec = setOracleReturnGeneratedValues(table, executeSpec)
            executeSpec
                .map { r ->
                    (table.table as AbstractTable<T>).toField(
                        tables.allColumns,
                        tables.allTables,
                        tables.dbType,
                    ).builder.invoke(r.toRow())
                }.one()
        }
    }

    private fun <T : Any> setOracleReturnGeneratedValues(
        kotysaTable: KotysaTable<T>,
        executeSpec: DatabaseClient.GenericExecuteSpec
    ): DatabaseClient.GenericExecuteSpec {
        if (tables.dbType != DbType.ORACLE) {
            return executeSpec
        }
        val allTableColumnNames = kotysaTable.columns
            .map { column -> column.name }
            .toTypedArray()
        return executeSpec.filter { statement, _ -> statement.returnGeneratedValues(*allTableColumnNames).execute() }
    }

    // fixme 13/12/21 : does not work if set to private
    fun <T : Any> insertExecuteSpec(
        row: T,
        table: KotysaTable<T>,
        sql: String,
    ): DatabaseClient.GenericExecuteSpec =
        table.dbColumns
            // filter out null or numeric values with negative or zero values with default value or Serial types
            .filterNot { column ->
                val value = column.entityGetter(row)
                ((value == null) || (value is Number && value.toLong() <= 0L)) &&
                        (column.defaultValue != null
                        || column.isAutoIncrement
                        || SqlType.SERIAL == column.sqlType
                        || SqlType.BIGSERIAL == column.sqlType)
            }
            .foldIndexed(client.sql(sql)) { index, execSpec, column ->
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
                            "k${index}",
                            (column.entityGetter.toCallable().returnType.classifier as KClass<*>).toDbClass().java
                        )
                    }
                } else {
                    execSpec.bind("k${index}", tables.getDbValue(value)!!)
                }
            }

    // fixme 13/12/21 : does not work if set to private
    fun <T : Any> fetchLastInserted(row: T, table: KotysaTable<T>): Mono<T> {
        val pkColumns = table.primaryKey.columns
        val pkFirstColumn = pkColumns.elementAt(0)
        val firstPkValue = pkFirstColumn.entityGetter(row)
        
        val executeSpec = if (
            pkColumns.size == 1 &&
            pkFirstColumn.isAutoIncrement &&
            ((firstPkValue == null) || (firstPkValue is Number && firstPkValue.toLong() <= 0L))
        ) {
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
                    tables.allTables,
                    tables.dbType,
                ).builder.invoke(r.toRow())
            }.one()
    }

    fun <T : Any> executeCreateTable(table: Table<T>, ifNotExists: Boolean): Mono<Void> {
        val createTableResult = createTableSql(table, ifNotExists)
        return client.sql(createTableResult.sql)
            .then()
            .then(
                // 2) loop to execute create indexes
                createTableResult.createIndexes.fold(Mono.empty()) { mono, createIndexResult ->
                    mono.then(
                        client.sql(createIndexResult.sql)
                            .then()
                            .onErrorResume(NonTransientDataAccessException::class) { ntdae ->
                                if (!ifNotExists || ntdae.message?.contains(createIndexResult.name, true) != true) {
                                    throw ntdae
                                }
                                Mono.empty()
                            }
                    )
                }
            )
    }
}

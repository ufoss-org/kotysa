/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.ufoss.kotysa.*
import java.math.BigDecimal
import io.r2dbc.spi.Connection
import io.r2dbc.spi.Statement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.awaitSingle
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.KClass

/**
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
internal class SqlClientR2dbc(
    private val connection: Connection,
    override val tables: Tables
) : CoroutinesSqlClient, DefaultSqlClient {

    override val module = Module.R2DBC

    override suspend fun <T : Any> insert(row: T) {
        val table = tables.getTable(row::class)

        val statement = connection.createStatement(insertSql(row))
        setStatementParams(row, table, statement)

        statement.execute().awaitSingle()
    }

    override suspend fun <T : Any> insert(vararg rows: T) {
        rows.forEach { row -> insert(row) }
    }

    override suspend fun <T : Any> insertAndReturn(row: T): T {
        val table = tables.getTable(row::class)
        return if (tables.dbType == DbType.MYSQL) {
            // For MySQL : insert, then fetch created tuple
            val statement = connection.createStatement(insertSql(row))
            setStatementParams(row, table, statement)
            statement.execute().awaitSingle()
            fetchLastInserted(row, table)
        } else {
            // other DB types have RETURNING style features
            val statement = connection.createStatement(insertSql(row, true))
            setStatementParams(row, table, statement)
            statement.execute().awaitSingle()
                .map { r, _ ->
                    (table.table as AbstractTable<T>).toField(
                        tables.allColumns,
                        tables.allTables,
                    ).builder.invoke(r.toRow())
                }.awaitSingle()
        }
    }

    override fun <T : Any> insertAndReturn(vararg rows: T): Flow<T> =
        rows.asFlow()
            .map { row -> insertAndReturn(row) }

    private fun <T : Any> setStatementParams(row: T, table: KotysaTable<T>, statement: Statement) {
        table.columns
            // do nothing for null values with default or Serial type
            .filterNot { column ->
                column.entityGetter(row) == null
                        && (column.defaultValue != null
                        || column.isAutoIncrement
                        || SqlType.SERIAL == column.sqlType
                        || SqlType.BIGSERIAL == column.sqlType)
            }
            .forEachIndexed { index, column ->
                val value = column.entityGetter(row)
                if (value == null) {
                    statement.bindNull(
                        "k${index}",
                        (column.entityGetter.toCallable().returnType.classifier as KClass<*>).toDbClass().java
                    )
                } else {
                    statement.bind("k${index}", tables.getDbValue(value)!!)
                }
            }
    }

    private suspend fun <T : Any> fetchLastInserted(row: T, table: KotysaTable<T>): T {
        @Suppress("UNCHECKED_CAST")
        val pkColumns = table.primaryKey.columns as List<DbColumn<T, *>>
        val statement = connection.createStatement(lastInsertedSql(row))

        if (
            pkColumns.size != 1 ||
            !pkColumns[0].isAutoIncrement ||
            pkColumns[0].entityGetter(row) != null
        ) {
            // bind all PK values
            pkColumns
                .map { column -> tables.getDbValue(column.entityGetter(row)) }
                .forEachIndexed { index, dbValue -> statement.bind("k${index}", dbValue!!) }
        }

        return statement.execute().awaitSingle()
            .map { r, _ ->
                (table.table as AbstractTable<T>).toField(
                    tables.allColumns,
                    tables.allTables
                ).builder.invoke(r.toRow())
            }.awaitSingle()
    }

    override suspend fun <T : Any> createTable(table: Table<T>) {
        createTable(table, false)
    }

    override suspend fun <T : Any> createTableIfNotExists(table: Table<T>) {
        createTable(table, true)
    }

    private suspend fun <T : Any> createTable(table: Table<T>, ifNotExists: Boolean) {
        val createTableSql = createTableSql(table, ifNotExists)
        connection.createStatement(createTableSql)
            .execute().awaitSingle()
    }

    override fun <T : Any> deleteFrom(table: Table<T>): CoroutinesSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        SqlClientDeleteJdbc.FirstDelete(connection, tables, table)

    override fun <T : Any> update(table: Table<T>): CoroutinesSqlClientDeleteOrUpdate.Update<T> =
        SqlClientUpdateJdbc.FirstUpdate(connection, tables, table)

    override fun <T : Any, U : Any> select(column: Column<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(connection, tables).select(column)
    
    override fun <T : Any> select(table: Table<T>): CoroutinesSqlClientSelect.FirstSelect<T> =
        SqlClientSelectJdbc.Selectable(connection, tables).select(table)
    
    override fun <T : Any> select(dsl: (ValueProvider) -> T): CoroutinesSqlClientSelect.Fromable<T> =
        SqlClientSelectJdbc.Selectable(connection, tables).select(dsl)
    
    override fun selectCount(): CoroutinesSqlClientSelect.Fromable<Long> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectCount<Any>(null)
    
    override fun <T : Any> selectCount(column: Column<*, T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectCount(column)
    
    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectDistinct(column)
    
    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectMin(column)
    
    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<U> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectMax(column)
    
    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): CoroutinesSqlClientSelect.FirstSelect<BigDecimal> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectAvg(column)
    
    override fun <T : Any> selectSum(column: IntColumn<T>): CoroutinesSqlClientSelect.FirstSelect<Long> =
        SqlClientSelectJdbc.Selectable(connection, tables).selectSum(column)
}

internal fun KClass<*>.toDbClass() =
    when (this.qualifiedName) {
        "kotlinx.datetime.LocalDate" -> LocalDate::class
        "kotlinx.datetime.LocalDateTime" -> LocalDateTime::class
        else -> this
    }

/**
 * Create a [SqlClient] from a R2DBC [Connection] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public fun Connection.sqlClient(tables: Tables): CoroutinesSqlClient = SqlClientR2dbc(this, tables)

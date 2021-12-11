/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteStatement
import org.ufoss.kotysa.*
import java.math.BigDecimal

/**
 * @sample org.ufoss.kotysa.android.sample.UserRepositorySqLite
 */
internal class SqlClientSqLite(
    private val client: SQLiteOpenHelper,
    override val tables: Tables
) : SqlClient, DefaultSqlClient {

    override val module = Module.SQLITE

    override fun <T : Any> insert(row: T) {
        val table = tables.getTable(row::class)
        val statement = client.writableDatabase.compileStatement(insertSql(row))
        setInsertStatementParams(row, table, statement)

        statement.execute()
    }

    /**
     * To speed these insert up, use inside a transaction
     */
    override fun <T : Any> insert(vararg rows: T) {
        val firstRow = rows[0]
        val table = tables.getTable(firstRow::class)
        val statement = client.writableDatabase.compileStatement(insertSql(firstRow))
        rows.forEach { row ->
            statement.clearBindings()
            setInsertStatementParams(row, table, statement)
            statement.execute()
        }
    }

    override fun <T : Any> insertAndReturn(row: T): T {
        val table = tables.getTable(row::class)
        val statement = client.writableDatabase.compileStatement(insertSql(row))
        setInsertStatementParams(row, table, statement)

        // For SqLite : insert, then fetch created tuple
        val lastId = statement.executeInsert()
        val cursor = client.readableDatabase.rawQuery(lastInsertedSql(row), arrayOf("$lastId"))
        cursor.moveToFirst()
        return (table.table as AbstractTable<T>).toField(
            tables.allColumns,
            tables.allTables,
        ).builder.invoke(cursor.toRow())
    }

    override fun <T : Any> insertAndReturn(vararg rows: T) = rows.map { row -> insertAndReturn(row) }

    private fun <T : Any> setInsertStatementParams(row: T, table: KotysaTable<T>, statement: SQLiteStatement) {
        table.columns
            // do nothing for null values with default
            .filterNot { column ->
                column.entityGetter(row) == null &&
                        (column.defaultValue != null || column.isAutoIncrement)
            }
            .forEachIndexed { index, column -> statement.bind(index + 1, column.entityGetter(row)) }
    }

    override fun <T : Any> createTable(table: Table<T>) {
        createTable(table, false)
    }

    override fun <T : Any> createTableIfNotExists(table: Table<T>) {
        createTable(table, true)
    }

    private fun <T : Any> createTable(table: Table<T>, ifNotExists: Boolean) {
        val createTableSql = createTableSql(table, ifNotExists)
        client.writableDatabase.compileStatement(createTableSql).execute()
    }

    override fun <T : Any> deleteFrom(table: Table<T>): SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        SqlClientDeleteSqLite.FirstDelete(client.writableDatabase, tables, table)

    override fun <T : Any> update(table: Table<T>): SqlClientDeleteOrUpdate.Update<T> =
        SqlClientUpdateSqLite.FirstUpdate(client.writableDatabase, tables, table)

    override fun <T : Any, U : Any> select(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).select(column)

    override fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).select(table)

    override fun <T : Any> select(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).select(dsl)

    override fun selectCount(): SqlClientSelect.Fromable<Long> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).selectCount<Any>(null)

    override fun <T : Any> selectCount(column: Column<*, T>): SqlClientSelect.FirstSelect<Long> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).selectCount(column)

    override fun <T : Any, U : Any> selectDistinct(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).selectDistinct(column)

    override fun <T : Any, U : Any> selectMin(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).selectMin(column)

    override fun <T : Any, U : Any> selectMax(column: MinMaxColumn<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).selectMax(column)

    override fun <T : Any, U : Any> selectAvg(column: NumericColumn<T, U>): SqlClientSelect.FirstSelect<BigDecimal> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).selectAvg(column)

    override fun <T : Any> selectSum(column: IntColumn<T>): SqlClientSelect.FirstSelect<Long> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).selectSum(column)
}

/**
 * Create a [SqlClient] from a Android SqLite [SQLiteDatabase] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.android.sample.UserRepositorySqLite
 */
public fun SQLiteOpenHelper.sqlClient(tables: Tables): SqlClient = SqlClientSqLite(this, tables)

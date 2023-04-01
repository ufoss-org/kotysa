/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.SQLException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteStatement
import org.ufoss.kotysa.*
import java.math.BigDecimal

/**
 * @sample org.ufoss.kotysa.android.sample.UserRepositorySqLite
 */
internal class SqlClientSqLite internal constructor(
    private val client: SQLiteOpenHelper,
    override val tables: SqLiteTables,
) : SqLiteSqlClient, DefaultSqlClient {

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
        require(rows.isNotEmpty()) { "rows must contain at least one element" }
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
        client.readableDatabase.rawQuery(lastInsertedSql(row), arrayOf("$lastId")).use { cursor ->
            cursor.moveToFirst()
            return (table.table as AbstractTable<T>).toField(
                tables.allColumns,
                tables.allTables,
                tables.dbType,
            ).builder.invoke(cursor.toRow())
        }
    }

    override fun <T : Any> insertAndReturn(vararg rows: T) = rows.map { row -> insertAndReturn(row) }

    private fun <T : Any> setInsertStatementParams(row: T, table: KotysaTable<T>, statement: SQLiteStatement) {
        table.dbColumns
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
        val createTableResult = createTableSql(table, ifNotExists)
        // 1) execute create table
        client.writableDatabase.execSQL(createTableResult.sql)
        // 2) loop to execute create indexes
        createTableResult.createIndexes.forEach { createIndexResult ->
            try {
                client.writableDatabase.execSQL(createIndexResult.sql)
            } catch (se: SQLException) {
                // if not exists : accept Index already exists error
                if (!ifNotExists || se.message?.contains(createIndexResult.name, true) != true) {
                    throw se
                }
            }
        }
    }

    override fun <T : Any> deleteFrom(table: Table<T>): SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T> =
        SqlClientDeleteSqLite.FirstDelete(client.writableDatabase, tables, table)

    override fun <T : Any> update(table: Table<T>): SqlClientDeleteOrUpdate.Update<T> =
        SqlClientUpdateSqLite.FirstUpdate(client.writableDatabase, tables, table)

    override fun <T : Any, U : Any> select(column: Column<T, U>): SqlClientSelect.FirstSelect<U> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).select(column)

    override fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).select(table)

    override fun <T : Any> selectAndBuild(dsl: (ValueProvider) -> T): SqlClientSelect.Fromable<T> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).selectAndBuild(dsl)

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

    override fun <T : Any> select(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.FirstSelect<T> = SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).select(dsl)

    override fun <T : Any> selectCaseWhenExists(
        dsl: SqlClientSubQuery.SingleScope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.SelectCaseWhenExistsFirst<T> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).selectCaseWhenExists(dsl)

    override fun <T : Any> selectStarFrom(
        dsl: SqlClientSubQuery.Scope.() -> SqlClientSubQuery.Return<T>
    ): SqlClientSelect.From<T> =
        SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).selectStarFromSubQuery(dsl)
}

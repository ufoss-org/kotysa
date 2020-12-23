/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.*

/**
 * @sample org.ufoss.kotysa.android.sample.UserRepositorySqLite
 */
internal class SqlClientSqLite(
        private val client: SQLiteOpenHelper,
        override val tables: Tables
) : SqlClient, DefaultSqlClient {

    override fun <T : Any> insert(row: T) {
        val table = tables.getTable(row::class)

        val statement = client.writableDatabase.compileStatement(insertSql(row))
        table.columns
                .filterNot { column -> column.entityGetter(row) == null && column.defaultValue != null }
                .forEachIndexed { index, column -> statement.bind(index + 1, column.entityGetter(row)) }

        statement.executeInsert()
    }

    override fun <T : Any> insert(vararg rows: T) {
        checkRowsAreMapped(*rows)

        rows.forEach { row -> insert(row) }
    }

    override fun <T : Any> createTable(table: Table<T>) {
        val createTableSql = createTableSql(table)
        client.writableDatabase.compileStatement(createTableSql).execute()
    }

    override fun <T : Any> deleteFrom(table: Table<T>): SqlClientDeleteOrUpdate.DeleteOrUpdate<T> =
            SqlClientDeleteSqLite.Delete(client.writableDatabase, tables, table)

    override fun <T : Any> update(table: Table<T>): SqlClientDeleteOrUpdate.Update<T> =
            SqlClientUpdateSqLite.Update(client.writableDatabase, tables, table)

    override fun <T : Any, U : Any> select(column: ColumnNotNull<T, U>): SqlClientSelect.FirstSelect<U> =
            SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).select(column)
    override fun <T : Any, U : Any> select(column: ColumnNullable<T, U>): SqlClientSelect.FirstSelect<U?> =
            SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).select(column)
    override fun <T : Any> select(table: Table<T>): SqlClientSelect.FirstSelect<T> =
            SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).select(table)

    override fun <T : Any> selectFrom(table: Table<T>): SqlClientSelect.From<T, T> =
            SqlClientSelectSqLite.Selectable(client.readableDatabase, tables).select(table).from(table)
}

/**
 * Create a [SqlClient] from a Android SqLite [SQLiteDatabase] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.android.sample.UserRepositorySqLite
 */
public fun SQLiteOpenHelper.sqlClient(tables: Tables): SqlClient = SqlClientSqLite(this, tables)

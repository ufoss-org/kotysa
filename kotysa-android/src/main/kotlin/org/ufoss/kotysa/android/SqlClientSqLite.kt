/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.*
import kotlin.reflect.KClass

/**
 * @sample org.ufoss.kotysa.android.sample.UserRepositorySqLite
 */
internal class SqlClientSqLite(
        private val client: SQLiteOpenHelper,
        override val tables: Tables
) : SqlClient(), DefaultSqlClient {

    override fun <T : Any> select(
            resultClass: KClass<T>,
            dsl: (SelectDslApi.(ValueProvider) -> T)?
    ): SqlClientSelect.Select<T> =
            SqlClientSelectSqLite.Select(client.readableDatabase, tables, resultClass, dsl)

    override fun <T : Any> createTable(tableClass: KClass<T>) {
        val createTableSql = createTableSql(tableClass)
        return client.writableDatabase.compileStatement(createTableSql).execute()
    }

    override fun <T : Any> insert(row: T) {
        val table = tables.getTable(row::class)

        val statement = client.writableDatabase.compileStatement(insertSql(row))
        table.columns.values
                .filterNot { column -> column.entityGetter(row) == null && column.defaultValue != null }
                .forEachIndexed { index, column -> statement.bind(index + 1, column.entityGetter(row)) }

        statement.executeInsert()
    }

    override fun insert(vararg rows: Any) {
        checkRowsAreMapped(*rows)

        rows.forEach { row -> insert(row) }
    }

    override fun <T : Any> deleteFromTable(tableClass: KClass<T>): SqlClientDeleteOrUpdate.DeleteOrUpdate<T> =
            SqlClientDeleteSqLite.Delete(client.writableDatabase, tables, tableClass)

    override fun <T : Any> updateTable(tableClass: KClass<T>): SqlClientDeleteOrUpdate.Update<T> =
            SqlClientUpdateSqLite.Update(client.writableDatabase, tables, tableClass)
}

/**
 * Create a [SqlClient] from a Android SqLite [SQLiteDatabase] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.android.sample.UserRepositorySqLite
 */
public fun SQLiteOpenHelper.sqlClient(tables: Tables): SqlClient = SqlClientSqLite(this, tables)

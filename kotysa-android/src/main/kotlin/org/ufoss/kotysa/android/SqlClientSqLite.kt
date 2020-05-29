/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

/**
 * @sample org.ufoss.kotysa.android.sample.UserRepositorySqLite
 */
internal class SqlClientSqLite(
        private val client: SQLiteOpenHelper,
        override val tables: Tables
) : BlockingSqlClient(), DefaultSqlClient {

    override fun <T : Any> select(resultClass: KClass<T>,
                                  dsl: (SelectDslApi.(ValueProvider) -> T)?): BlockingSqlClientSelect.Select<T> =
            SqlClientSelectSqLite.Select(client.readableDatabase, tables, resultClass, dsl)

    override fun <T : Any> createTable(tableClass: KClass<T>) {
        val createTableSql = createTableSql(tableClass)
        return client.writableDatabase.execSQL(createTableSql)
    }

    override fun <T : Any> insert(row: T) {
        val table = tables.getTable(row::class)
        val contentValues = ContentValues(table.columns.size)
        table.columns.values
                .filterNot { column -> column.entityGetter(row) == null && column.defaultValue != null }
                .forEach { column -> contentValues.put(column.name, column.entityGetter(row)) }

        // debug query
        insertSqlDebug(row)

        client.writableDatabase.insert(table.name, null, contentValues)
    }

    override fun insert(vararg rows: Any) {
        checkRowsAreMapped(*rows)

        rows.forEach { row -> insert(row) }
    }

    override fun <T : Any> deleteFromTable(tableClass: KClass<T>): BlockingSqlClientDeleteOrUpdate.DeleteOrUpdate<T> =
            SqlClientDeleteSqLite.Delete(client.writableDatabase, tables, tableClass)

    override fun <T : Any> updateTable(tableClass: KClass<T>): BlockingSqlClientDeleteOrUpdate.Update<T> =
            SqlClientUpdateSqLite.Update(client.writableDatabase, tables, tableClass)
}

internal fun ContentValues.put(name: String, value: Any?) {
    if (value != null) {
        when (value) {
            is Int -> put(name, value)
            is Byte -> put(name, value)
            is Long -> put(name, value)
            is Float -> put(name, value)
            is Short -> put(name, value)
            is Double -> put(name, value)
            is String -> put(name, value)
            is Boolean -> put(name, value)
            is ByteArray -> put(name, value)
            // Date are stored as String
            is LocalDate -> put(name, value.format(DateTimeFormatter.ISO_LOCAL_DATE))
            is LocalDateTime -> put(name, value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            is OffsetDateTime -> put(name, value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
            is LocalTime -> put(name, value.format(DateTimeFormatter.ISO_LOCAL_TIME))
            else -> throw UnsupportedOperationException(
                    "${value.javaClass.canonicalName} is not supported by Android SqLite")
        }
    } else {
        putNull(name)
    }
}

/**
 * Create a [BlockingSqlClient] from a Android SqLite [SQLiteDatabase] with [Tables] mapping
 *
 * @sample org.ufoss.kotysa.android.sample.UserRepositorySqLite
 */
public fun SQLiteOpenHelper.sqlClient(tables: Tables): BlockingSqlClient = SqlClientSqLite(this, tables)

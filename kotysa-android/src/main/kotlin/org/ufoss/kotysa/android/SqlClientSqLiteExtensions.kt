/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteStatement
import org.ufoss.kotysa.DefaultSqlClientCommon
import org.ufoss.kotysa.RowImpl
import org.ufoss.kotysa.SqLiteSqlClient
import org.ufoss.kotysa.SqLiteTables
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * Create a [SqLiteSqlClient] from a Android SqLite [SQLiteDatabase] with [SqLiteTables] mapping
 *
 * @sample org.ufoss.kotysa.android.sample.UserRepositorySqLite
 */
public fun SQLiteOpenHelper.sqlClient(tables: SqLiteTables): SqLiteSqlClient = SqlClientSqLite(this, tables)

internal fun SQLiteStatement.bind(index: Int, value: Any?) {
    if (value != null) {
        when (value) {
            is Boolean -> bindLong(index, if (value) 1L else 0L)
            is Short -> bindLong(index, value.toLong())
            is Int -> bindLong(index, value.toLong())
            is Long -> bindLong(index, value)
            is Float -> bindDouble(index, value.toDouble())
            is Double -> bindDouble(index, value)
            is String -> bindString(index, value)
            is ByteArray -> bindBlob(index, value)
            // Dates are stored as String
            is LocalDate -> bindString(index, value.format(DateTimeFormatter.ISO_LOCAL_DATE))
            is LocalDateTime -> bindString(index, value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            is OffsetDateTime -> bindString(index, value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
            is LocalTime -> bindString(index, value.format(DateTimeFormatter.ISO_LOCAL_TIME))
            else -> when (value::class.qualifiedName) {
                "kotlinx.datetime.LocalDate" -> bindString(index, value.toString())
                "kotlinx.datetime.LocalDateTime" -> {
                    val kotlinxLocalDateTime = value as kotlinx.datetime.LocalDateTime
                    if (kotlinxLocalDateTime.second == 0 && kotlinxLocalDateTime.nanosecond == 0) {
                        bindString(index, "$value:00") // missing seconds
                    } else {
                        bindString(index, value.toString())
                    }
                }
                "kotlinx.datetime.LocalTime" -> bindString(index, value.toString())

                else -> throw UnsupportedOperationException(
                    "${value.javaClass.canonicalName} is not supported by Android SqLite"
                )
            }
        }
    } else {
        bindNull(index)
    }
}

internal fun DefaultSqlClientCommon.Properties.bindParameters(statement: SQLiteStatement) =
    parameterValues()
        .forEachIndexed { index, parameter -> statement.bind(index + 1, parameter) }

internal fun Cursor.toRow() = RowImpl(SqLiteRow(this))

internal fun DefaultSqlClientCommon.Properties.parameterValues(): List<Any?> =
    parameters
        .flatMap { parameter ->
            if (parameter is Collection<*>) {
                parameter
            } else {
                setOf(parameter)
            }
        }

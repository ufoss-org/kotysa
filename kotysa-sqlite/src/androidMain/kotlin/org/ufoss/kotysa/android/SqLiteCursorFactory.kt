/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteCursorDriver
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteQuery
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

internal class SqLiteCursorFactory(private val params: List<Any?>) : CursorFactory {
    
    override fun newCursor(
        db: SQLiteDatabase,
        masterQuery: SQLiteCursorDriver,
        editTable: String?,
        query: SQLiteQuery
    ): Cursor {
        params.forEachIndexed { index, param ->
            when (param) {
                null -> query.bindNull(index + 1)
                is Boolean -> query.bindString(index + 1, if (param) "1" else "0")
                is Short -> query.bindLong(index + 1, param.toLong())
                is Int -> query.bindLong(index + 1, param.toLong())
                is Long -> query.bindLong(index + 1, param)
                is Float -> query.bindDouble(index + 1, param.toDouble())
                is Double -> query.bindDouble(index + 1, param)
                is String -> query.bindString(index + 1, param)
                is ByteArray -> query.bindBlob(index + 1, param)
                // Dates are stored as String
                is LocalDate -> query.bindString(index + 1, param.format(DateTimeFormatter.ISO_LOCAL_DATE))
                is LocalDateTime -> query.bindString(index + 1, param.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                is LocalTime -> query.bindString(index + 1, param.format(DateTimeFormatter.ISO_LOCAL_TIME))
                is OffsetDateTime -> query.bindString(index + 1, param.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                else -> when (param::class.qualifiedName) {
                    "kotlinx.datetime.LocalDate" -> query.bindString(index + 1, "$param")
                    "kotlinx.datetime.LocalDateTime" -> {
                        val kotlinxLocalDateTime = param as kotlinx.datetime.LocalDateTime
                        if (kotlinxLocalDateTime.second == 0 && kotlinxLocalDateTime.nanosecond == 0) {
                            query.bindString(index + 1, "$kotlinxLocalDateTime:00") // missing seconds
                        } else {
                            query.bindString(index + 1, "$kotlinxLocalDateTime")
                        }
                    }
                    "kotlinx.datetime.LocalTime" -> query.bindString(index + 1, "$param")
                    else -> throw RuntimeException("${param.javaClass.canonicalName} is not supported yet")
                }
            }
            if (param == null) {
                query.bindNull(index + 1)
            }
        }
        return SQLiteCursor(masterQuery, editTable, query)
    }
}
/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteStatement
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

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
            // Date are stored as String
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
                else -> throw UnsupportedOperationException(
                        "${value.javaClass.canonicalName} is not supported by Android SqLite"
                )
            }
        }
    } else {
        bindNull(index)
    }
}
/*
internal fun DefaultSqlClientCommon.Return.bindWhereArgs(statement: SQLiteStatement, idx: Int = 1) = with(properties) {
    var index = idx
    whereValues()
            .forEach { whereValue -> statement.bind(index++, whereValue) }
}

internal fun DefaultSqlClientCommon.Return.buildWhereArgs(): Array<String>? = with(properties) {
    return if (whereClauses.isNotEmpty()) {
        whereValues()
                .map { whereValue -> stringValue(whereValue) }
                .toTypedArray()
    } else {
        null
    }
}

private fun DefaultSqlClientCommon.Return.whereValues(): List<Any?> = with(properties) {
    whereClauses
            .mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
            .flatMap { whereValue ->
                if (whereValue is Collection<*>) {
                    whereValue
                } else {
                    setOf(whereValue)
                }
            }
}*/

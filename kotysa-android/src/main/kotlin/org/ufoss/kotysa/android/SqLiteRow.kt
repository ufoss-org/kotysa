/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.Cursor
import org.ufoss.kotysa.Row
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
internal class SqLiteRow internal constructor(private val sqLiteCursor: Cursor) : Row {
    override fun <T : Any> get(index: Int, clazz: Class<T>) =
            if (sqLiteCursor.isNull(index)) {
                null
            } else {
                when {
                    Integer::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getInt(index)
                    java.lang.Long::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getLong(index)
                    java.lang.Float::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getFloat(index)
                    java.lang.Short::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getShort(index)
                    java.lang.Double::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getDouble(index)
                    String::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getString(index)
                    // boolean is stored as Int
                    java.lang.Boolean::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getInt(index) != 0
                    ByteArray::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getBlob(index)
                    // Date are stored as String
                    LocalDate::class.java.isAssignableFrom(clazz) -> LocalDate.parse(sqLiteCursor.getString(index))
                    LocalDateTime::class.java.isAssignableFrom(clazz) -> LocalDateTime.parse(
                            sqLiteCursor.getString(index)
                    )
                    OffsetDateTime::class.java.isAssignableFrom(clazz) -> OffsetDateTime.parse(
                            sqLiteCursor.getString(index)
                    )
                    LocalTime::class.java.isAssignableFrom(clazz) -> LocalTime.parse(sqLiteCursor.getString(index))
                    else -> when (clazz.name) {
                        "kotlinx.datetime.LocalDate" -> kotlinx.datetime.LocalDate.parse(
                                sqLiteCursor.getString(index)
                        )
                        "kotlinx.datetime.LocalDateTime" -> kotlinx.datetime.LocalDateTime.parse(
                                sqLiteCursor.getString(index)
                        )
                        else -> throw UnsupportedOperationException(
                                "${clazz.canonicalName} is not supported by Android SqLite"
                        )
                    }
                } as T?
            }
}

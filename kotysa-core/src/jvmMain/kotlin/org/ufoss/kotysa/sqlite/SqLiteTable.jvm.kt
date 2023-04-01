/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sqlite

import org.ufoss.kotysa.AbstractTable
import org.ufoss.kotysa.columns.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

/**
 * Represents a SqLite Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [SqLite Data types](https://www.sqlite.org/datatype3.html)
 * @param T Entity type associated with this table
 */
public actual abstract class SqLiteTable<T : Any> protected actual constructor(tableName: String?) :
    AbstractTable<T>(tableName) {

    protected actual fun text(getter: (T) -> String, columnName: String?): StringDbTextColumnNotNull<T> =
        StringDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected actual fun text(
        getter: (T) -> String?,
        columnName: String?,
        defaultValue: String?
    ): StringDbTextColumnNullable<T> =
        StringDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun text(getter: (T) -> LocalDateTime, columnName: String? = null): LocalDateTimeDbTextColumnNotNull<T> =
        LocalDateTimeDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun text(
        getter: (T) -> LocalDateTime?, columnName: String? = null, defaultValue: LocalDateTime? = null
    ): LocalDateTimeDbTextColumnNullable<T> =
        LocalDateTimeDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun text(getter: (T) -> LocalDate, columnName: String? = null): LocalDateDbTextColumnNotNull<T> =
        LocalDateDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun text(
        getter: (T) -> LocalDate?, columnName: String? = null, defaultValue: LocalDate? = null
    ): LocalDateDbTextColumnNullable<T> =
        LocalDateDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun text(
        getter: (T) -> OffsetDateTime,
        columnName: String? = null
    ): OffsetDateTimeDbTextColumnNotNull<T> =
        OffsetDateTimeDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun text(
        getter: (T) -> OffsetDateTime?, columnName: String? = null, defaultValue: OffsetDateTime? = null
    ): OffsetDateTimeDbTextColumnNullable<T> =
        OffsetDateTimeDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun text(getter: (T) -> LocalTime, columnName: String? = null): LocalTimeDbTextColumnNotNull<T> =
        LocalTimeDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun text(
        getter: (T) -> LocalTime?, columnName: String? = null, defaultValue: LocalTime? = null
    ): LocalTimeDbTextColumnNullable<T> =
        LocalTimeDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected actual fun integer(getter: (T) -> Int, columnName: String?): IntDbIntColumnNotNull<T> =
        IntDbIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected actual fun integer(
        getter: (T) -> Int?,
        columnName: String?,
        defaultValue: Int?
    ): IntDbIntColumnNullable<T> =
        IntDbIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected actual fun autoIncrementInteger(getter: (T) -> Int?, columnName: String?): IntDbIntColumnNotNull<T> =
        IntDbIntColumnNotNull(getter, columnName, true).also { addColumn(it) }

    protected actual fun integer(getter: (T) -> Long, columnName: String?): LongDbIntColumnNotNull<T> =
            LongDbIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected actual fun integer(
        getter: (T) -> Long?,
        columnName: String?,
        defaultValue: Long?
    ): LongDbIntColumnNullable<T> =
            LongDbIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected actual fun autoIncrementInteger(getter: (T) -> Long?, columnName: String?): LongDbIntColumnNotNull<T> =
            LongDbIntColumnNotNull(getter, columnName, true).also { addColumn(it) }

    protected actual fun real(getter: (T) -> Float, columnName: String?): FloatDbRealColumnNotNull<T> =
        FloatDbRealColumnNotNull(getter, columnName).also { addColumn(it) }

    protected actual fun real(
        getter: (T) -> Float?,
        columnName: String?,
        defaultValue: Float?
    ): FloatDbRealColumnNullable<T> = FloatDbRealColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected actual fun real(getter: (T) -> Double, columnName: String?): DoubleDbRealColumnNotNull<T> =
        DoubleDbRealColumnNotNull(getter, columnName).also { addColumn(it) }

    protected actual fun real(
        getter: (T) -> Double?,
        columnName: String?,
        defaultValue: Double?
    ): DoubleDbRealColumnNullable<T> =
        DoubleDbRealColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected actual fun integer(getter: (T) -> Boolean, columnName: String?): BooleanDbIntColumnNotNull<T> =
        BooleanDbIntColumnNotNull(getter, columnName).also { addColumn(it) }

    protected actual fun blob(getter: (T) -> ByteArray, columnName: String?): ByteArrayDbBlobColumnNotNull<T> =
        ByteArrayDbBlobColumnNotNull(getter, columnName).also { addColumn(it) }

    protected actual fun blob(getter: (T) -> ByteArray?, columnName: String?): ByteArrayDbBlobColumnNullable<T> =
        ByteArrayDbBlobColumnNullable(getter, columnName).also { addColumn(it) }
}

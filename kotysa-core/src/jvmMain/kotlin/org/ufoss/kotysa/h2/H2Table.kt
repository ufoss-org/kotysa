/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.h2

import org.ufoss.kotysa.AbstractCommonTable
import org.ufoss.kotysa.columns.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

/**
 * Represents a H2 Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [H2 Data types](http://h2database.com/html/datatypes.html)
 * @param T Entity type associated with this table
 */
public actual abstract class H2Table<T : Any> protected actual constructor(tableName: String?) :
    AbstractCommonTable<T>(tableName) {

    protected fun varchar(
        getter: (T) -> String,
        columnName: String? = null,
        size: Int? = null
    ): StringDbVarcharColumnNotNull<T> =
        StringDbVarcharColumnNotNull(getter, columnName, size).also { addColumn(it) }

    protected fun varchar(
        getter: (T) -> String?, columnName: String? = null, defaultValue: String? = null,
        size: Int? = null
    ): StringDbVarcharColumnNullable<T> =
        StringDbVarcharColumnNullable(getter, columnName, defaultValue, size).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int, columnName: String? = null): IntDbIntColumnNotNull<T> =
        IntDbIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun integer(
        getter: (T) -> Int?,
        columnName: String? = null,
        defaultValue: Int? = null
    ): IntDbIntColumnNullable<T> = IntDbIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun autoIncrementInteger(getter: (T) -> Int?, columnName: String? = null): IntDbIntColumnNotNull<T> =
        IntDbIntColumnNotNull(getter, columnName, true).also { addColumn(it) }

    protected fun bigInt(getter: (T) -> Long, columnName: String? = null): LongDbBigIntColumnNotNull<T> =
        LongDbBigIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun bigInt(
        getter: (T) -> Long?,
        columnName: String? = null,
        defaultValue: Long? = null
    ): LongDbBigIntColumnNullable<T> =
        LongDbBigIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun autoIncrementBigInt(getter: (T) -> Long?, columnName: String? = null): LongDbBigIntColumnNotNull<T> =
        LongDbBigIntColumnNotNull(getter, columnName, true).also { addColumn(it) }

    protected fun real(getter: (T) -> Float, columnName: String? = null): FloatDbRealColumnNotNull<T> =
        FloatDbRealColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun real(
        getter: (T) -> Float?,
        columnName: String? = null,
        defaultValue: Float? = null
    ): FloatDbRealColumnNullable<T> = FloatDbRealColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun doublePrecision(getter: (T) -> Double, columnName: String? = null)
            : DoubleDbDoublePrecisionColumnNotNull<T> =
        DoubleDbDoublePrecisionColumnNotNull(getter, columnName, null, null).also { addColumn(it) }

    protected fun doublePrecision(
        getter: (T) -> Double?,
        columnName: String? = null,
        defaultValue: Double? = null
    ): DoubleDbDoublePrecisionColumnNullable<T> =
        DoubleDbDoublePrecisionColumnNullable(getter, columnName, defaultValue, null, null)
            .also { addColumn(it) }

    protected fun numeric(getter: (T) -> BigDecimal, precision: Int, scale: Int, columnName: String? = null)
            : BigDecimalDbNumericColumnNotNull<T> =
        BigDecimalDbNumericColumnNotNull(getter, columnName, precision, scale).also { addColumn(it) }

    protected fun numeric(
        getter: (T) -> BigDecimal?,
        precision: Int,
        scale: Int,
        columnName: String? = null,
        defaultValue: BigDecimal? = null,
    ): BigDecimalDbNumericColumnNullable<T> =
        BigDecimalDbNumericColumnNullable(getter, columnName, defaultValue, precision, scale).also { addColumn(it) }

    protected fun decimal(getter: (T) -> BigDecimal, precision: Int, scale: Int, columnName: String? = null)
            : BigDecimalDbDecimalColumnNotNull<T> =
        BigDecimalDbDecimalColumnNotNull(getter, columnName, precision, scale).also { addColumn(it) }

    protected fun decimal(
        getter: (T) -> BigDecimal?,
        precision: Int,
        scale: Int,
        columnName: String? = null,
        defaultValue: BigDecimal? = null,
    ): BigDecimalDbDecimalColumnNullable<T> =
        BigDecimalDbDecimalColumnNullable(getter, columnName, defaultValue, precision, scale).also { addColumn(it) }

    protected fun boolean(getter: (T) -> Boolean, columnName: String? = null): BooleanDbBooleanColumnNotNull<T> =
        BooleanDbBooleanColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun date(getter: (T) -> LocalDate, columnName: String? = null): LocalDateDbDateColumnNotNull<T> =
        LocalDateDbDateColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun date(
        getter: (T) -> LocalDate?,
        columnName: String? = null,
        defaultValue: LocalDate? = null
    ): LocalDateDbDateColumnNullable<T> =
        LocalDateDbDateColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun dateTime(
        getter: (T) -> LocalDateTime,
        columnName: String? = null,
        precision: Int? = null
    ): LocalDateTimeDbDateTimeColumnNotNull<T> =
        LocalDateTimeDbDateTimeColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun dateTime(
        getter: (T) -> LocalDateTime?,
        columnName: String? = null,
        defaultValue: LocalDateTime? = null,
        precision: Int? = null
    ): LocalDateTimeDbDateTimeColumnNullable<T> =
        LocalDateTimeDbDateTimeColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

    protected fun timestamp(
        getter: (T) -> LocalDateTime,
        columnName: String? = null,
        precision: Int? = null
    ): LocalDateTimeDbTimestampColumnNotNull<T> =
        LocalDateTimeDbTimestampColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun timestamp(
        getter: (T) -> LocalDateTime?,
        columnName: String? = null,
        defaultValue: LocalDateTime? = null,
        precision: Int? = null
    ): LocalDateTimeDbTimestampColumnNullable<T> =
        LocalDateTimeDbTimestampColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

    protected fun timestampWithTimeZone(
        getter: (T) -> OffsetDateTime,
        columnName: String? = null,
        precision: Int? = null
    ): OffsetDateTimeDbTimestampWithTimeZoneColumnNotNull<T> =
        OffsetDateTimeDbTimestampWithTimeZoneColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun timestampWithTimeZone(
        getter: (T) -> OffsetDateTime?,
        columnName: String? = null,
        defaultValue: OffsetDateTime? = null,
        precision: Int? = null
    ): OffsetDateTimeDbTimestampWithTimeZoneColumnNullable<T> =
        OffsetDateTimeDbTimestampWithTimeZoneColumnNullable(
            getter,
            columnName,
            defaultValue,
            precision
        ).also { addColumn(it) }

    protected fun time(
        getter: (T) -> LocalTime,
        columnName: String? = null,
        precision: Int? = null
    ): LocalTimeDbTimeColumnNotNull<T> =
        LocalTimeDbTimeColumnNotNull(getter, columnName, precision).also { addColumn(it) }

    protected fun time(
        getter: (T) -> LocalTime?,
        columnName: String? = null,
        defaultValue: LocalTime? = null,
        precision: Int? = null
    ): LocalTimeDbTimeColumnNullable<T> =
        LocalTimeDbTimeColumnNullable(getter, columnName, defaultValue, precision).also { addColumn(it) }

    protected fun uuid(getter: (T) -> UUID, columnName: String? = null): UuidDbUuidColumnNotNull<T> =
        UuidDbUuidColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun uuid(
        getter: (T) -> UUID?,
        columnName: String? = null,
        defaultValue: UUID? = null
    ): UuidDbUuidColumnNullable<T> =
        UuidDbUuidColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun blob(getter: (T) -> ByteArray, columnName: String? = null): ByteArrayDbBlobColumnNotNull<T> =
        ByteArrayDbBlobColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun blob(
        getter: (T) -> ByteArray?,
        columnName: String? = null
    ): ByteArrayDbBlobColumnNullable<T> =
        ByteArrayDbBlobColumnNullable(getter, columnName).also { addColumn(it) }

    protected fun binary(getter: (T) -> ByteArray, columnName: String? = null): ByteArrayDbBinaryColumnNotNull<T> =
        ByteArrayDbBinaryColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun binary(getter: (T) -> ByteArray?, columnName: String? = null): ByteArrayDbBinaryColumnNullable<T> =
        ByteArrayDbBinaryColumnNullable(getter, columnName).also { addColumn(it) }
}

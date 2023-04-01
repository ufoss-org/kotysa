/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.oracle

import org.ufoss.kotysa.AbstractCommonTable
import org.ufoss.kotysa.columns.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime

/**
 * Represents a Oracle Table
 *
 * **Extend this class with an object**
 *
 * supported types follow :
 * [Oracle Data types](https://docs.oracle.com/database/121/SQLRF/sql_elements001.htm#SQLRF0021) and
 * [Type mapping](https://docs.oracle.com/cd/A97335_02/apps.102/a83724/basic3.htm)
 * @param T Entity type associated with this table
 */
public actual abstract class OracleTable<T : Any> protected actual constructor(tableName: String?) :
    AbstractCommonTable<T>(tableName) {

    protected fun varchar2(
        getter: (T) -> String,
        columnName: String? = null,
        size: Int = 255
    ): StringDbVarchar2ColumnNotNull<T> =
        StringDbVarchar2ColumnNotNull(getter, columnName, size).also { addColumn(it) }

    protected fun varchar2(
        getter: (T) -> String?,
        columnName: String? = null,
        defaultValue: String? = null,
        size: Int = 255
    ): StringDbVarchar2ColumnNullable<T> =
        StringDbVarchar2ColumnNullable(getter, columnName, defaultValue, size).also { addColumn(it) }

    protected fun number(getter: (T) -> Int, columnName: String? = null, size: Int = 10): IntDbNumberColumnNotNull<T> =
        IntDbNumberColumnNotNull(getter, columnName, size).also { addColumn(it) }

    protected fun number(
        getter: (T) -> Int?,
        columnName: String? = null,
        size: Int = 10,
        defaultValue: Int? = null
    ): IntDbNumberColumnNullable<T> =
        IntDbNumberColumnNullable(getter, columnName, defaultValue, size).also { addColumn(it) }

    protected fun number(getter: (T) -> Long, columnName: String? = null, size: Int = 19)
            : LongDbNumberColumnNotNull<T> = LongDbNumberColumnNotNull(getter, columnName, size).also { addColumn(it) }

    protected fun number(
        getter: (T) -> Long?,
        columnName: String? = null,
        size: Int = 19,
        defaultValue: Long? = null
    ): LongDbNumberColumnNullable<T> =
        LongDbNumberColumnNullable(getter, columnName, defaultValue, size).also { addColumn(it) }

    protected fun binaryFloat(getter: (T) -> Float, columnName: String? = null): FloatDbBinaryFloatColumnNotNull<T> =
        FloatDbBinaryFloatColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun binaryFloat(
        getter: (T) -> Float?,
        columnName: String? = null,
        defaultValue: Float? = null
    ): FloatDbBinaryFloatColumnNullable<T> =
        FloatDbBinaryFloatColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun binaryDouble(getter: (T) -> Double, columnName: String? = null)
            : DoubleDbBinaryDoubleColumnNotNull<T> =
        DoubleDbBinaryDoubleColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun binaryDouble(
        getter: (T) -> Double?,
        columnName: String? = null,
        defaultValue: Double? = null,
    ): DoubleDbBinaryDoubleColumnNullable<T> =
        DoubleDbBinaryDoubleColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun number(getter: (T) -> BigDecimal, precision: Int, scale: Int, columnName: String? = null)
            : BigDecimalDbNumberColumnNotNull<T> =
        BigDecimalDbNumberColumnNotNull(getter, columnName, precision, scale).also { addColumn(it) }

    protected fun number(
        getter: (T) -> BigDecimal?,
        precision: Int,
        scale: Int,
        columnName: String? = null,
        defaultValue: BigDecimal? = null,
    ): BigDecimalDbNumberColumnNullable<T> =
        BigDecimalDbNumberColumnNullable(getter, columnName, defaultValue, precision, scale).also { addColumn(it) }

    protected fun number(getter: (T) -> Boolean, columnName: String? = null): BooleanDbNumberColumnNotNull<T> =
        BooleanDbNumberColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun date(getter: (T) -> LocalDate, columnName: String? = null): LocalDateDbDateColumnNotNull<T> =
        LocalDateDbDateColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun date(
        getter: (T) -> LocalDate?,
        columnName: String? = null,
        defaultValue: LocalDate? = null
    ): LocalDateDbDateColumnNullable<T> =
        LocalDateDbDateColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

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

    protected fun raw(
        getter: (T) -> ByteArray,
        columnName: String? = null,
        size: Int = 255
    ): ByteArrayDbRawColumnNotNull<T> =
        ByteArrayDbRawColumnNotNull(getter, columnName, size).also { addColumn(it) }

    protected fun raw(
        getter: (T) -> ByteArray?,
        columnName: String? = null,
        size: Int = 255
    ): ByteArrayDbRawColumnNullable<T> =
        ByteArrayDbRawColumnNullable(getter, columnName, size).also { addColumn(it) }
}

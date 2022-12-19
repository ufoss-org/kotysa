/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.mssql

import org.ufoss.kotysa.AbstractTable
import org.ufoss.kotysa.columns.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Represents a Microsoft SQL Server Table
 *
 * **Extend this class with an object**
 *
 * supported types follow :
 * [Microsoft SQL Server Data types](https://docs.microsoft.com/en-us/sql/t-sql/data-types/data-types-transact-sql?view=sql-server-ver15)
 * @param T Entity type associated with this table
 */
public abstract class MssqlTable<T : Any> protected constructor(tableName: String? = null) :
    AbstractTable<T>(tableName) {

    protected fun varchar(
        getter: (T) -> String,
        columnName: String? = null,
        size: Int = 255
    ): StringDbVarcharColumnNotNull<T> =
        StringDbVarcharColumnNotNull(getter, columnName, size).also { addColumn(it) }

    protected fun varchar(
        getter: (T) -> String?, columnName: String? = null, defaultValue: String? = null,
        size: Int = 255
    ): StringDbVarcharColumnNullable<T> =
        StringDbVarcharColumnNullable(getter, columnName, defaultValue, size).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int, columnName: String? = null): IntDbIntColumnNotNull<T> =
        IntDbIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun integer(
        getter: (T) -> Int?,
        columnName: String? = null,
        defaultValue: Int? = null
    ): IntDbIntColumnNullable<T> =
        IntDbIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun identityInteger(getter: (T) -> Int?, columnName: String? = null): IntDbIntColumnNotNull<T> =
        IntDbIntColumnNotNull(getter, columnName, true).also { addColumn(it) }

    protected fun bigInt(getter: (T) -> Long, columnName: String? = null): LongDbBigIntColumnNotNull<T> =
        LongDbBigIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun bigInt(
        getter: (T) -> Long?,
        columnName: String? = null,
        defaultValue: Long? = null
    ): LongDbBigIntColumnNullable<T> =
        LongDbBigIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun identityBigInt(getter: (T) -> Long?, columnName: String? = null): LongDbBigIntColumnNotNull<T> =
        LongDbBigIntColumnNotNull(getter, columnName, true).also { addColumn(it) }

    protected fun real(getter: (T) -> Float, columnName: String? = null): FloatDbRealColumnNotNull<T> =
        FloatDbRealColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun real(
        getter: (T) -> Float?,
        columnName: String? = null,
        defaultValue: Float? = null
    ): FloatDbRealColumnNullable<T> = FloatDbRealColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun float(getter: (T) -> Double, columnName: String? = null, size: Int? = null)
            : DoubleDbFloatColumnNotNull<T> =
        DoubleDbFloatColumnNotNull(getter, columnName, size).also { addColumn(it) }

    protected fun float(
        getter: (T) -> Double?,
        columnName: String? = null,
        defaultValue: Double? = null,
        size: Int? = null,
    ): DoubleDbFloatColumnNullable<T> =
        DoubleDbFloatColumnNullable(getter, columnName, defaultValue, size).also { addColumn(it) }

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

    protected fun bit(getter: (T) -> Boolean, columnName: String? = null): BooleanDbBitColumnNotNull<T> =
        BooleanDbBitColumnNotNull(getter, columnName).also { addColumn(it) }

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

    protected fun binary(getter: (T) -> ByteArray, columnName: String? = null): ByteArrayDbBinaryColumnNotNull<T> =
        ByteArrayDbBinaryColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun binary(getter: (T) -> ByteArray?, columnName: String? = null): ByteArrayDbBinaryColumnNullable<T> =
        ByteArrayDbBinaryColumnNullable(getter, columnName).also { addColumn(it) }
}

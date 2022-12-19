/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.AbstractTable
import org.ufoss.kotysa.columns.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

/**
 * Represents a PostgreSQL Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [Postgresql data types](https://www.postgresql.org/docs/11/datatype.html)
 * More here : [Postgresql data types mapped Java classes]
 * (https://www.postgresql.org/message-id/AANLkTikkkxN+-UUiGVTzj8jdfS4PdpB8_tDONMFHNqHk@mail.gmail.com)
 * @param T Entity type associated with this table
 */
public abstract class PostgresqlTable<T : Any> protected constructor(tableName: String? = null) :
    AbstractTable<T>(tableName) {

    protected fun varchar(
        getter: (T) -> String,
        columnName: String? = null,
        size: Int? = null
    ): StringDbVarcharColumnNotNull<T> = StringDbVarcharColumnNotNull(getter, columnName, size).also { addColumn(it) }

    protected fun varchar(
        getter: (T) -> String?, columnName: String? = null, defaultValue: String? = null,
        size: Int? = null
    ): StringDbVarcharColumnNullable<T> =
        StringDbVarcharColumnNullable(getter, columnName, defaultValue, size).also { addColumn(it) }

    protected fun text(getter: (T) -> String, columnName: String? = null): StringDbTextColumnNotNull<T> =
        StringDbTextColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun text(
        getter: (T) -> String?, columnName: String? = null, defaultValue: String? = null
    ): StringDbTextColumnNullable<T> =
        StringDbTextColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun integer(getter: (T) -> Int, columnName: String? = null): IntDbIntColumnNotNull<T> =
        IntDbIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun integer(
        getter: (T) -> Int?,
        columnName: String? = null,
        defaultValue: Int? = null
    ): IntDbIntColumnNullable<T> = IntDbIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun bigInt(getter: (T) -> Long, columnName: String? = null): LongDbBigIntColumnNotNull<T> =
        LongDbBigIntColumnNotNull(getter, columnName, false).also { addColumn(it) }

    protected fun bigInt(
        getter: (T) -> Long?,
        columnName: String? = null,
        defaultValue: Long? = null
    ): LongDbBigIntColumnNullable<T> =
        LongDbBigIntColumnNullable(getter, columnName, defaultValue).also { addColumn(it) }

    protected fun serial(getter: (T) -> Int?, columnName: String? = null): IntDbSerialColumnNotNull<T> =
        IntDbSerialColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun bigSerial(getter: (T) -> Long?, columnName: String? = null): LongDbBigSerialColumnNotNull<T> =
        LongDbBigSerialColumnNotNull(getter, columnName).also { addColumn(it) }

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

    protected fun bytea(getter: (T) -> ByteArray, columnName: String? = null): ByteArrayDbByteaColumnNotNull<T> =
        ByteArrayDbByteaColumnNotNull(getter, columnName).also { addColumn(it) }

    protected fun bytea(getter: (T) -> ByteArray?, columnName: String? = null): ByteArrayDbByteaColumnNullable<T> =
        ByteArrayDbByteaColumnNullable(getter, columnName).also { addColumn(it) }

    protected fun tsvector(tsvectorType: TsvectorType, vararg columns: AbstractDbColumn<T, *>): TsvectorColumn<T> {
        require(columns.isNotEmpty()) { "columns must contain at least one element" }
        return TsvectorColumn(null, tsvectorType.name, columns).also { addColumn(it) }
    }

    protected fun tsvector(tsvectorType: TsvectorType, columnName: String, vararg columns: AbstractDbColumn<T, *>)
            : TsvectorColumn<T> {
        require(columns.isNotEmpty()) { "columns must contain at least one element" }
        return TsvectorColumn(columnName, tsvectorType.name, columns).also { addColumn(it) }
    }

    protected fun tsvector(tsvectorType: String, vararg columns: AbstractDbColumn<T, *>): TsvectorColumn<T> {
        require(columns.isNotEmpty()) { "columns must contain at least one element" }
        return TsvectorColumn(null, tsvectorType, columns).also { addColumn(it) }
    }

    protected fun tsvector(tsvectorType: String, vararg columns: AbstractDbColumn<T, *>, columnName: String)
            : TsvectorColumn<T> {
        require(columns.isNotEmpty()) { "columns must contain at least one element" }
        return TsvectorColumn(columnName, tsvectorType, columns).also { addColumn(it) }
    }
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

/**
 * Represents a column
 *
 * @param T Entity type associated with the table this column is in
 * @param U return type of associated getter to this column
 */
public interface Column<out T : Any, U : Any>

public interface MinMaxColumn<out T : Any, U : Any> : Column<T, U>
public interface NumericColumn<out T : Any, U : Any> : Column<T, U>

/**
 * Not null column, its return type is <[U]>
 */
public interface ColumnNotNull<out T : Any, U : Any> : Column<T, U>

/**
 * Nullable column, its return type is <[U]?>
 */
public interface ColumnNullable<out T : Any, U : Any> : Column<T, U>

public interface StringColumn<out T : Any> : Column<T, String>, MinMaxColumn<T, String>
public interface StringColumnNotNull<out T : Any> : StringColumn<T>, ColumnNotNull<T, String>
public interface StringColumnNullable<out T : Any> : StringColumn<T>, ColumnNullable<T, String>

public interface LocalDateTimeColumn<out T : Any> : Column<T, LocalDateTime>, MinMaxColumn<T, LocalDateTime>
public interface LocalDateTimeColumnNotNull<out T : Any> : LocalDateTimeColumn<T>, ColumnNotNull<T, LocalDateTime>
public interface LocalDateTimeColumnNullable<out T : Any> : LocalDateTimeColumn<T>, ColumnNullable<T, LocalDateTime>

public interface KotlinxLocalDateTimeColumn<out T : Any> : Column<T, kotlinx.datetime.LocalDateTime>,
        MinMaxColumn<T, kotlinx.datetime.LocalDateTime>
public interface KotlinxLocalDateTimeColumnNotNull<out T : Any> :
        KotlinxLocalDateTimeColumn<T>, ColumnNotNull<T, kotlinx.datetime.LocalDateTime>
public interface KotlinxLocalDateTimeColumnNullable<out T : Any> :
        KotlinxLocalDateTimeColumn<T>, ColumnNullable<T, kotlinx.datetime.LocalDateTime>

public interface LocalDateColumn<out T : Any> : Column<T, LocalDate>, MinMaxColumn<T, LocalDate>
public interface LocalDateColumnNotNull<out T : Any> : LocalDateColumn<T>, ColumnNotNull<T, LocalDate>
public interface LocalDateColumnNullable<out T : Any> : LocalDateColumn<T>, ColumnNullable<T, LocalDate>

public interface KotlinxLocalDateColumn<out T : Any> : Column<T, kotlinx.datetime.LocalDate>,
        MinMaxColumn<T, kotlinx.datetime.LocalDate>
public interface KotlinxLocalDateColumnNotNull<out T : Any> :
        KotlinxLocalDateColumn<T>, ColumnNotNull<T, kotlinx.datetime.LocalDate>
public interface KotlinxLocalDateColumnNullable<out T : Any> :
        KotlinxLocalDateColumn<T>, ColumnNullable<T, kotlinx.datetime.LocalDate>

public interface OffsetDateTimeColumn<out T : Any> : Column<T, OffsetDateTime>, MinMaxColumn<T, OffsetDateTime>
public interface OffsetDateTimeColumnNotNull<out T : Any> : OffsetDateTimeColumn<T>, ColumnNotNull<T, OffsetDateTime>
public interface OffsetDateTimeColumnNullable<out T : Any> : OffsetDateTimeColumn<T>, ColumnNullable<T, OffsetDateTime>

public interface LocalTimeColumn<out T : Any> : Column<T, LocalTime>, MinMaxColumn<T, LocalTime>
public interface LocalTimeColumnNotNull<out T : Any> : LocalTimeColumn<T>, ColumnNotNull<T, LocalTime>
public interface LocalTimeColumnNullable<out T : Any> : LocalTimeColumn<T>, ColumnNullable<T, LocalTime>

public interface BooleanColumnNotNull<out T : Any> : ColumnNotNull<T, Boolean>

public interface IntColumn<out T : Any> : Column<T, Int>, MinMaxColumn<T, Int>, NumericColumn<T, Int>
public interface IntColumnNotNull<out T : Any> : IntColumn<T>, ColumnNotNull<T, Int>
public interface IntColumnNullable<out T : Any> : IntColumn<T>, ColumnNullable<T, Int>

public interface LongColumn<out T : Any> : Column<T, Long>, MinMaxColumn<T, Long>, NumericColumn<T, Long>
public interface LongColumnNotNull<out T : Any> : LongColumn<T>, ColumnNotNull<T, Long>
public interface LongColumnNullable<out T : Any> : LongColumn<T>, ColumnNullable<T, Long>

public interface UuidColumn<out T : Any> : Column<T, UUID>
public interface UuidColumnNotNull<out T : Any> : UuidColumn<T>, ColumnNotNull<T, UUID>
public interface UuidColumnNullable<out T : Any> : UuidColumn<T>, ColumnNullable<T, UUID>

// Extension functions

@Suppress("UNCHECKED_CAST")
internal fun <T : Any, U: Any> Column<T, U>.getKotysaColumn(availableColumns: Map<Column<*, *>, KotysaColumn<*, *>>): KotysaColumn<T, U> {
    return requireNotNull(availableColumns[this]) { "Requested column \"$this\" is not mapped" } as KotysaColumn<T, U>
}

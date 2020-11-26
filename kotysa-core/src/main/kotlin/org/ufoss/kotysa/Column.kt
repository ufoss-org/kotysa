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
public interface Column<T : Any, U : Any>

/**
 * Not null column, its return type is <[U]>
 */
public interface ColumnNotNull<T : Any, U : Any> : Column<T, U>

/**
 * Nullable column, its return type is <[U]?>
 */
public interface ColumnNullable<T : Any, U : Any> : Column<T, U>

public interface StringColumn<T : Any> : Column<T, String>
public interface StringColumnNotNull<T : Any> : StringColumn<T>, ColumnNotNull<T, String>
public interface StringColumnNullable<T : Any> : StringColumn<T>, ColumnNullable<T, String>

public interface LocalDateTimeColumn<T : Any> : Column<T, LocalDateTime>
public interface LocalDateTimeColumnNotNull<T : Any> : LocalDateTimeColumn<T>, ColumnNotNull<T, LocalDateTime>
public interface LocalDateTimeColumnNullable<T : Any> : LocalDateTimeColumn<T>, ColumnNullable<T, LocalDateTime>

public interface KotlinxLocalDateTimeColumn<T : Any> : Column<T, kotlinx.datetime.LocalDateTime>
public interface KotlinxLocalDateTimeColumnNotNull<T : Any> :
        KotlinxLocalDateTimeColumn<T>, ColumnNotNull<T, kotlinx.datetime.LocalDateTime>
public interface KotlinxLocalDateTimeColumnNullable<T : Any> :
        KotlinxLocalDateTimeColumn<T>, ColumnNullable<T, kotlinx.datetime.LocalDateTime>

public interface LocalDateColumn<T : Any> : Column<T, LocalDate>
public interface LocalDateColumnNotNull<T : Any> : LocalDateColumn<T>, ColumnNotNull<T, LocalDate>
public interface LocalDateColumnNullable<T : Any> : LocalDateColumn<T>, ColumnNullable<T, LocalDate>

public interface KotlinxLocalDateColumn<T : Any> : Column<T, kotlinx.datetime.LocalDate>
public interface KotlinxLocalDateColumnNotNull<T : Any> :
        KotlinxLocalDateColumn<T>, ColumnNotNull<T, kotlinx.datetime.LocalDate>
public interface KotlinxLocalDateColumnNullable<T : Any> :
        KotlinxLocalDateColumn<T>, ColumnNullable<T, kotlinx.datetime.LocalDate>

public interface OffsetDateTimeColumn<T : Any> : Column<T, OffsetDateTime>
public interface OffsetDateTimeColumnNotNull<T : Any> : OffsetDateTimeColumn<T>, ColumnNotNull<T, OffsetDateTime>
public interface OffsetDateTimeColumnNullable<T : Any> : OffsetDateTimeColumn<T>, ColumnNullable<T, OffsetDateTime>

public interface LocalTimeColumn<T : Any> : Column<T, LocalTime>
public interface LocalTimeColumnNotNull<T : Any> : LocalTimeColumn<T>, ColumnNotNull<T, LocalTime>
public interface LocalTimeColumnNullable<T : Any> : LocalTimeColumn<T>, ColumnNullable<T, LocalTime>

public interface BooleanColumnNotNull<T : Any> : ColumnNotNull<T, Boolean>

public interface IntColumn<T : Any> : Column<T, Int>
public interface IntColumnNotNull<T : Any> : IntColumn<T>, ColumnNotNull<T, Int>
public interface IntColumnNullable<T : Any> : IntColumn<T>, ColumnNullable<T, Int>

public interface UuidColumn<T : Any> : Column<T, UUID>
public interface UuidColumnNotNull<T : Any> : UuidColumn<T>, ColumnNotNull<T, UUID>
public interface UuidColumnNullable<T : Any> : UuidColumn<T>, ColumnNullable<T, UUID>

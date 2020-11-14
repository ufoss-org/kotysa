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
 * Represents a Column
 *
 * @param T Entity type associated with the table this column is in
 * @param U return type of associated getter to this column
 */
public interface Column<T : Any, U : Any>

public interface ColumnNotNull<T : Any, U : Any> : Column<T, U>
public interface ColumnNullable<T : Any, U : Any> : Column<T, U>

public interface StringColumnNotNull<T : Any> : ColumnNotNull<T, String>
public interface StringColumnNullable<T : Any> : ColumnNullable<T, String>

public interface LocalDateTimeColumnNotNull<T : Any> : ColumnNotNull<T, LocalDateTime>
public interface LocalDateTimeColumnNullable<T : Any> : ColumnNullable<T, LocalDateTime>

public interface KotlinxLocalDateTimeColumnNotNull<T : Any> : ColumnNotNull<T, kotlinx.datetime.LocalDateTime>
public interface KotlinxLocalDateTimeColumnNullable<T : Any> : ColumnNullable<T, kotlinx.datetime.LocalDateTime>

public interface LocalDateColumnNotNull<T : Any> : ColumnNotNull<T, LocalDate>
public interface LocalDateColumnNullable<T : Any> : ColumnNullable<T, LocalDate>

public interface KotlinxLocalDateColumnNotNull<T : Any> : ColumnNotNull<T, kotlinx.datetime.LocalDate>
public interface KotlinxLocalDateColumnNullable<T : Any> : ColumnNullable<T, kotlinx.datetime.LocalDate>

public interface OffsetDateTimeColumnNotNull<T : Any> : ColumnNotNull<T, OffsetDateTime>
public interface OffsetDateTimeColumnNullable<T : Any> : ColumnNullable<T, OffsetDateTime>

public interface LocalTimeColumnNotNull<T : Any> : ColumnNotNull<T, LocalTime>
public interface LocalTimeColumnNullable<T : Any> : ColumnNullable<T, LocalTime>

public interface BooleanColumnNotNull<T : Any> : ColumnNotNull<T, Boolean>

public interface IntColumnNotNull<T : Any> : ColumnNotNull<T, Int>
public interface IntColumnNullable<T : Any> : ColumnNullable<T, Int>

public interface UuidColumnNotNull<T : Any> : ColumnNotNull<T, UUID>
public interface UuidColumnNullable<T : Any> : ColumnNullable<T, UUID>

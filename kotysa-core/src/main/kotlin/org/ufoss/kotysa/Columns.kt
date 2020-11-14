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

public interface StringColumnNotNull<T : Any> : Column<T, String>
public interface StringColumnNullable<T : Any> : Column<T, String>

public interface LocalDateTimeColumnNotNull<T : Any> : Column<T, LocalDateTime>
public interface LocalDateTimeColumnNullable<T : Any> : Column<T, LocalDateTime>

public interface KotlinxLocalDateTimeColumnNotNull<T : Any> : Column<T, kotlinx.datetime.LocalDateTime>
public interface KotlinxLocalDateTimeColumnNullable<T : Any> : Column<T, kotlinx.datetime.LocalDateTime>

public interface LocalDateColumnNotNull<T : Any> : Column<T, LocalDate>
public interface LocalDateColumnNullable<T : Any> : Column<T, LocalDate>

public interface KotlinxLocalDateColumnNotNull<T : Any> : Column<T, kotlinx.datetime.LocalDate>
public interface KotlinxLocalDateColumnNullable<T : Any> : Column<T, kotlinx.datetime.LocalDate>

public interface OffsetDateTimeColumnNotNull<T : Any> : Column<T, OffsetDateTime>
public interface OffsetDateTimeColumnNullable<T : Any> : Column<T, OffsetDateTime>

public interface LocalTimeColumnNotNull<T : Any> : Column<T, LocalTime>
public interface LocalTimeColumnNullable<T : Any> : Column<T, LocalTime>

public interface BooleanColumnNotNull<T : Any> : Column<T, Boolean>

public interface IntColumnNotNull<T : Any> : Column<T, Int>
public interface IntColumnNullable<T : Any> : Column<T, Int>

public interface UuidColumnNotNull<T : Any> : Column<T, UUID>
public interface UuidColumnNullable<T : Any> : Column<T, UUID>

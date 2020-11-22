/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*


public interface FieldSetter<T : Any> {

    public operator fun set(column: StringColumnNotNull<T>, value: String)

    public operator fun set(column: StringColumnNullable<T>, value: String?)

    public operator fun set(column: LocalDateTimeColumnNotNull<T>, value: LocalDateTime)

    public operator fun set(column: LocalDateTimeColumnNullable<T>, value: LocalDateTime?)

    public operator fun set(column: KotlinxLocalDateTimeColumnNotNull<T>, value: kotlinx.datetime.LocalDateTime)

    public operator fun set(column: KotlinxLocalDateTimeColumnNullable<T>, value: kotlinx.datetime.LocalDateTime?)

    public operator fun set(column: LocalDateColumnNotNull<T>, value: LocalDate)

    public operator fun set(column: LocalDateColumnNullable<T>, value: LocalDate?)

    public operator fun set(column: KotlinxLocalDateColumnNotNull<T>, value: kotlinx.datetime.LocalDate)

    public operator fun set(column: KotlinxLocalDateColumnNullable<T>, value: kotlinx.datetime.LocalDate?)

    public operator fun set(column: OffsetDateTimeColumnNotNull<T>, value: OffsetDateTime)

    public operator fun set(column: OffsetDateTimeColumnNullable<T>, value: OffsetDateTime?)

    public operator fun set(column: LocalTimeColumnNotNull<T>, value: LocalTime)

    public operator fun set(column: LocalTimeColumnNullable<T>, value: LocalTime?)

    public operator fun set(column: BooleanColumnNotNull<T>, value: Boolean)

    public operator fun set(column: UuidColumnNotNull<T>, value: UUID)

    public operator fun set(column: UuidColumnNullable<T>, value: UUID?)

    public operator fun set(column: IntColumnNotNull<T>, value: Int)

    public operator fun set(column: IntColumnNullable<T>, value: Int?)
}

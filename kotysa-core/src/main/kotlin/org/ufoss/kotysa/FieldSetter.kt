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

    public operator fun set(getter: (T) -> String, value: String)

    public operator fun set(getter: (T) -> String?, value: String?): Nullable

    public operator fun set(getter: (T) -> LocalDateTime, value: LocalDateTime)

    public operator fun set(getter: (T) -> LocalDateTime?, value: LocalDateTime?): Nullable

    public operator fun set(getter: (T) -> kotlinx.datetime.LocalDateTime, value: kotlinx.datetime.LocalDateTime)

    public operator fun set(getter: (T) -> kotlinx.datetime.LocalDateTime?, value: kotlinx.datetime.LocalDateTime?): Nullable

    public operator fun set(getter: (T) -> LocalDate, value: LocalDate)

    public operator fun set(getter: (T) -> LocalDate?, value: LocalDate?): Nullable

    public operator fun set(getter: (T) -> OffsetDateTime, value: OffsetDateTime)

    public operator fun set(getter: (T) -> OffsetDateTime?, value: OffsetDateTime?): Nullable

    public operator fun set(getter: (T) -> LocalTime, value: LocalTime)

    public operator fun set(getter: (T) -> LocalTime?, value: LocalTime?): Nullable

    public operator fun set(getter: (T) -> Boolean, value: Boolean)

    public operator fun set(getter: (T) -> UUID, value: UUID)

    public operator fun set(getter: (T) -> UUID?, value: UUID?): Nullable

    public operator fun set(getter: (T) -> Int, value: Int)

    public operator fun set(getter: (T) -> Int?, value: Int?): Nullable
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*


public interface ValueProvider {

    public operator fun <T : Any> get(getter: (T) -> String, alias: String? = null): String

    public operator fun <T : Any> get(getter: (T) -> String?, alias: String? = null, `_`: Nullable = Nullable.TRUE): String?

    public operator fun <T : Any> get(getter: (T) -> LocalDateTime, alias: String? = null): LocalDateTime

    public operator fun <T : Any> get(getter: (T) -> LocalDateTime?, alias: String? = null, `_`: Nullable = Nullable.TRUE): LocalDateTime?

    public operator fun <T : Any> get(getter: (T) -> kotlinx.datetime.LocalDateTime, alias: String? = null): kotlinx.datetime.LocalDateTime

    public operator fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDateTime?, alias: String? = null, `_`: Nullable = Nullable.TRUE
    ): kotlinx.datetime.LocalDateTime?

    public operator fun <T : Any> get(getter: (T) -> LocalDate, alias: String? = null): LocalDate

    public operator fun <T : Any> get(getter: (T) -> LocalDate?, alias: String? = null, `_`: Nullable = Nullable.TRUE): LocalDate?

    public operator fun <T : Any> get(getter: (T) -> kotlinx.datetime.LocalDate, alias: String? = null): kotlinx.datetime.LocalDate

    public operator fun <T : Any> get(
            getter: (T) -> kotlinx.datetime.LocalDate?, alias: String? = null, `_`: Nullable = Nullable.TRUE
    ): kotlinx.datetime.LocalDate?

    public operator fun <T : Any> get(getter: (T) -> OffsetDateTime, alias: String? = null): OffsetDateTime

    public operator fun <T : Any> get(getter: (T) -> OffsetDateTime?, alias: String? = null, `_`: Nullable = Nullable.TRUE): OffsetDateTime?

    public operator fun <T : Any> get(getter: (T) -> LocalTime, alias: String? = null): LocalTime

    public operator fun <T : Any> get(getter: (T) -> LocalTime?, alias: String? = null, `_`: Nullable = Nullable.TRUE): LocalTime?

    public operator fun <T : Any> get(getter: (T) -> Boolean, alias: String? = null): Boolean

    public operator fun <T : Any> get(getter: (T) -> UUID, alias: String? = null): UUID

    public operator fun <T : Any> get(getter: (T) -> UUID?, alias: String? = null, `_`: Nullable = Nullable.TRUE): UUID?

    public operator fun <T : Any> get(getter: (T) -> Int, alias: String? = null): Int

    public operator fun <T : Any> get(getter: (T) -> Int?, alias: String? = null, `_`: Nullable = Nullable.TRUE): Int?
}

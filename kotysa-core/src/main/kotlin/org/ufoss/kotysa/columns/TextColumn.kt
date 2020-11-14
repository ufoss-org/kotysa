/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.SqlType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

public sealed class TextColumn<T : Any, U : Any> : Column<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false
    // No size
    final override val size = null

    final override val sqlType = SqlType.TEXT
}

public sealed class TextColumnNotNull<T : Any, U : Any> : TextColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class StringTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> String?,
        override val name: String,
) : TextColumnNotNull<T, String>(), StringFieldColumnNotNull

public class StringTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> String?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: String?,
) : TextColumn<T, String>(), StringFieldColumnNullable

public class LocalDateTimeTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
) : TextColumnNotNull<T, LocalDateTime>(), LocalDateTimeFieldColumnNotNull

public class LocalDateTimeTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalDateTime?,
) : TextColumn<T, LocalDateTime>(), LocalDateTimeFieldColumnNullable

public class KotlinxLocalDateTimeTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
) : TextColumnNotNull<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeFieldColumnNotNull

public class KotlinxLocalDateTimeTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: kotlinx.datetime.LocalDateTime?,
) : TextColumn<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeFieldColumnNullable

public class LocalDateTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDate?,
        override val name: String,
) : TextColumnNotNull<T, LocalDate>(), LocalDateFieldColumnNotNull

public class LocalDateTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDate?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalDate?,
) : TextColumn<T, LocalDate>(), LocalDateFieldColumnNullable

public class KotlinxLocalDateTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDate?,
        override val name: String,
) : TextColumnNotNull<T, kotlinx.datetime.LocalDate>(), KotlinxLocalDateFieldColumnNotNull

public class KotlinxLocalDateTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDate?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: kotlinx.datetime.LocalDate?,
) : TextColumn<T, kotlinx.datetime.LocalDate>(), KotlinxLocalDateFieldColumnNullable

public class OffsetDateTimeTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
) : TextColumnNotNull<T, OffsetDateTime>(), OffsetDateTimeFieldColumnNotNull

public class OffsetDateTimeTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: OffsetDateTime?,
) : TextColumn<T, OffsetDateTime>(), OffsetDateTimeFieldColumnNullable

public class LocalTimeTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalTime?,
        override val name: String,
) : TextColumnNotNull<T, LocalTime>(), LocalTimeFieldColumnNotNull

public class LocalTimeTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalTime?,
) : TextColumn<T, LocalTime>(), LocalTimeFieldColumnNullable

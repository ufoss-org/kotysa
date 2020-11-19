/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

public sealed class DbTextColumn<T : Any, U : Any> : DbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false
    // No size
    final override val size = null

    final override val sqlType = SqlType.TEXT
}

public sealed class DbTextColumnNotNull<T : Any, U : Any> : DbTextColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class StringDbTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> String?,
        override val name: String?,
) : DbTextColumnNotNull<T, String>(), StringColumnNotNull<T>

public class StringDbTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> String?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: String?,
) : DbTextColumn<T, String>(), StringColumnNullable<T>

public class LocalDateTimeDbTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
) : DbTextColumnNotNull<T, LocalDateTime>(), LocalDateTimeColumnNotNull<T>

public class LocalDateTimeDbTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalDateTime?,
) : DbTextColumn<T, LocalDateTime>(), LocalDateTimeColumnNullable<T>

public class KotlinxLocalDateTimeDbTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
) : DbTextColumnNotNull<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeColumnNotNull<T>

public class KotlinxLocalDateTimeDbTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: kotlinx.datetime.LocalDateTime?,
) : DbTextColumn<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeColumnNullable<T>

public class LocalDateDbTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDate?,
        override val name: String,
) : DbTextColumnNotNull<T, LocalDate>(), LocalDateColumnNotNull<T>

public class LocalDateDbTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDate?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalDate?,
) : DbTextColumn<T, LocalDate>(), LocalDateColumnNullable<T>

public class KotlinxLocalDateDbTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDate?,
        override val name: String,
) : DbTextColumnNotNull<T, kotlinx.datetime.LocalDate>(), KotlinxLocalDateColumnNotNull<T>

public class KotlinxLocalDateDbTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDate?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: kotlinx.datetime.LocalDate?,
) : DbTextColumn<T, kotlinx.datetime.LocalDate>(), KotlinxLocalDateColumnNullable<T>

public class OffsetDateTimeDbTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
) : DbTextColumnNotNull<T, OffsetDateTime>(), OffsetDateTimeColumnNotNull<T>

public class OffsetDateTimeDbTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: OffsetDateTime?,
) : DbTextColumn<T, OffsetDateTime>(), OffsetDateTimeColumnNullable<T>

public class LocalTimeDbTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalTime?,
        override val name: String,
) : DbTextColumnNotNull<T, LocalTime>(), LocalTimeColumnNotNull<T>

public class LocalTimeDbTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalTime?,
) : DbTextColumn<T, LocalTime>(), LocalTimeColumnNullable<T>

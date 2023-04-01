/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

public class LocalDateTimeDbTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val columnName: String?,
) : DbTextColumnNotNull<T, LocalDateTime>(), LocalDateTimeColumnNotNull<T>

public class LocalDateTimeDbTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val columnName: String?,
        override val defaultValue: LocalDateTime?,
) : DbTextColumn<T, LocalDateTime>(), LocalDateTimeColumnNullable<T> {
    override val isNullable = defaultValue == null
}

public class LocalDateDbTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDate?,
        override val columnName: String?,
) : DbTextColumnNotNull<T, LocalDate>(), LocalDateColumnNotNull<T>

public class LocalDateDbTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDate?,
        override val columnName: String?,
        override val defaultValue: LocalDate?,
) : DbTextColumn<T, LocalDate>(), LocalDateColumnNullable<T> {
    override val isNullable = defaultValue == null
}

public class OffsetDateTimeDbTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val columnName: String?,
) : DbTextColumnNotNull<T, OffsetDateTime>(), OffsetDateTimeColumnNotNull<T>

public class OffsetDateTimeDbTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val columnName: String?,
        override val defaultValue: OffsetDateTime?,
) : DbTextColumn<T, OffsetDateTime>(), OffsetDateTimeColumnNullable<T> {
    override val isNullable = defaultValue == null
}

public class LocalTimeDbTextColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalTime?,
        override val columnName: String?,
) : DbTextColumnNotNull<T, LocalTime>(), LocalTimeColumnNotNull<T>

public class LocalTimeDbTextColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalTime?,
        override val columnName: String?,
        override val defaultValue: LocalTime?,
) : DbTextColumn<T, LocalTime>(), LocalTimeColumnNullable<T> {
    override val isNullable = defaultValue == null
}

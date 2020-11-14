/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.SqlType
import java.time.LocalDateTime

public sealed class DateTimeColumn<T : Any, U : Any> : Column<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false

    final override val sqlType = SqlType.DATE_TIME
}


public sealed class DateTimeColumnNotNull<T : Any, U : Any> : DateTimeColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class LocalDateTimeDateTimeColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
        override val size: Int?,
) : DateTimeColumnNotNull<T, LocalDateTime>(), LocalDateTimeFieldColumnNotNull

public class LocalDateTimeDateTimeColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalDateTime?,
        override val size: Int?,
) : DateTimeColumn<T, LocalDateTime>(), LocalDateTimeFieldColumnNullable

public class KotlinxLocalDateTimeDateTimeColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
        override val size: Int?,
) : DateTimeColumnNotNull<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeFieldColumnNotNull

public class KotlinxLocalDateTimeDateTimeColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: kotlinx.datetime.LocalDateTime?,
        override val size: Int?,
) : DateTimeColumn<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeFieldColumnNullable

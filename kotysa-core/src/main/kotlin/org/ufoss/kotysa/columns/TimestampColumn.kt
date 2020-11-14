/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.SqlType
import java.time.LocalDateTime
import java.time.OffsetDateTime

public sealed class TimestampColumn<T : Any, U : Any> : Column<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false

    final override val sqlType = SqlType.TIMESTAMP
}


public sealed class TimestampColumnNotNull<T : Any, U : Any> : TimestampColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class LocalDateTimeTimestampColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
        override val size: Int?,
) : TimestampColumnNotNull<T, LocalDateTime>(), LocalDateTimeFieldColumnNotNull

public class LocalDateTimeTimestampColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: LocalDateTime?,
        override val size: Int?,
) : TimestampColumn<T, LocalDateTime>(), LocalDateTimeFieldColumnNullable

public class KotlinxLocalDateTimeTimestampColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
        override val size: Int?,
) : TimestampColumnNotNull<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeFieldColumnNotNull

public class KotlinxLocalDateTimeTimestampColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: kotlinx.datetime.LocalDateTime?,
        override val size: Int?,
) : TimestampColumn<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeFieldColumnNullable

public class OffsetDateTimeTimestampColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
        override val size: Int?,
) : TimestampColumnNotNull<T, OffsetDateTime>(), OffsetDateTimeFieldColumnNotNull

public class OffsetDateTimeTimestampColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: OffsetDateTime?,
        override val size: Int?,
) : TimestampColumn<T, OffsetDateTime>(), OffsetDateTimeFieldColumnNullable

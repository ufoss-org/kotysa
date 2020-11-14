/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.SqlType
import java.time.OffsetDateTime

public sealed class TimestampWithTimeZoneColumn<T : Any, U : Any> : Column<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false

    final override val sqlType = SqlType.TIMESTAMP_WITH_TIME_ZONE
}

public sealed class TimestampWithTimeZoneColumnNotNull<T : Any, U : Any> : TimestampWithTimeZoneColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class OffsetDateTimeTimestampWithTimeZoneColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
        override val size: Int?,
) : TimestampWithTimeZoneColumnNotNull<T, OffsetDateTime>(), OffsetDateTimeFieldColumnNotNull

public class OffsetDateTimeTimestampWithTimeZoneColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: OffsetDateTime?,
        override val size: Int?,
) : TimestampWithTimeZoneColumn<T, OffsetDateTime>(), OffsetDateTimeFieldColumnNullable

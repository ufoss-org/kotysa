/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType
import java.time.OffsetDateTime

internal interface TimestampWithTimeZoneColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U> {
    override val sqlType get() = SqlType.TIMESTAMP_WITH_TIME_ZONE
}

public abstract class TimestampWithTimeZoneColumnNotNull<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), TimestampWithTimeZoneColumn<T, U>, ColumnNotNull<T, U>

public abstract class TimestampWithTimeZoneColumnNullable<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), TimestampWithTimeZoneColumn<T, U>, ColumnNullable<T, U>

public class OffsetDateTimeTimestampWithTimeZoneColumnNotNull<T : Any>(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
        override val size: Int?,
) : TimestampWithTimeZoneColumnNotNull<T, OffsetDateTime>(), OffsetDateTimeFieldColumnNotNull

public class OffsetDateTimeTimestampWithTimeZoneColumnNullable<T : Any>(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: OffsetDateTime?,
        override val size: Int?,
) : TimestampWithTimeZoneColumnNullable<T, OffsetDateTime>(), OffsetDateTimeFieldColumnNullable

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.DbColumn
import org.ufoss.kotysa.OffsetDateTimeColumnNotNull
import org.ufoss.kotysa.OffsetDateTimeColumnNullable
import org.ufoss.kotysa.SqlType
import java.time.OffsetDateTime

public sealed class DbTimestampWithTimeZoneColumn<T : Any, U : Any> : DbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false

    final override val sqlType = SqlType.TIMESTAMP_WITH_TIME_ZONE
}

public sealed class DbTimestampWithTimeZoneColumnNotNull<T : Any, U : Any> : DbTimestampWithTimeZoneColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class OffsetDateTimeDbTimestampWithTimeZoneColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String?,
        override val size: Int?,
) : DbTimestampWithTimeZoneColumnNotNull<T, OffsetDateTime>(), OffsetDateTimeColumnNotNull<T>

public class OffsetDateTimeDbTimestampWithTimeZoneColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val name: String?,
        override val defaultValue: OffsetDateTime?,
        override val size: Int?,
) : DbTimestampWithTimeZoneColumn<T, OffsetDateTime>(), OffsetDateTimeColumnNullable<T> {
    override val isNullable = defaultValue == null
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.OffsetDateTimeColumnNotNull
import org.ufoss.kotysa.OffsetDateTimeColumnNullable
import org.ufoss.kotysa.SqlType
import java.time.OffsetDateTime

public sealed class DbDateTimeOffsetColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    // No decimals
    final override val scale = null

    final override val sqlType = SqlType.DATETIMEOFFSET
}

public sealed class DbDateTimeOffsetColumnNotNull<T : Any, U : Any> : DbDateTimeOffsetColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class OffsetDateTimeDbDateTimeOffsetColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val columnName: String?,
        override val size: Int?,
) : DbDateTimeOffsetColumnNotNull<T, OffsetDateTime>(), OffsetDateTimeColumnNotNull<T>

public class OffsetDateTimeDbDateTimeOffsetColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val columnName: String?,
        override val defaultValue: OffsetDateTime?,
        override val size: Int?,
) : DbDateTimeOffsetColumn<T, OffsetDateTime>(), OffsetDateTimeColumnNullable<T> {
    override val isNullable = defaultValue == null
}

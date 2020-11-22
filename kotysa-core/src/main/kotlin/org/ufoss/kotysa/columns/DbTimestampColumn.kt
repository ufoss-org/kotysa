/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDateTime
import java.time.OffsetDateTime

public sealed class DbTimestampColumn<T : Any, U : Any> : DbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false

    final override val sqlType = SqlType.TIMESTAMP
}


public sealed class DbTimestampColumnNotNull<T : Any, U : Any> : DbTimestampColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class LocalDateTimeDbTimestampColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val columnName: String?,
        override val size: Int?,
) : DbTimestampColumnNotNull<T, LocalDateTime>(), LocalDateTimeColumnNotNull<T>

public class LocalDateTimeDbTimestampColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val columnName: String?,
        override val defaultValue: LocalDateTime?,
        override val size: Int?,
) : DbTimestampColumn<T, LocalDateTime>(), LocalDateTimeColumnNullable<T> {
    override val isNullable = defaultValue == null
}

public class KotlinxLocalDateTimeDbTimestampColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val columnName: String?,
        override val size: Int?,
) : DbTimestampColumnNotNull<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeColumnNotNull<T>

public class KotlinxLocalDateTimeDbTimestampColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val columnName: String?,
        override val defaultValue: kotlinx.datetime.LocalDateTime?,
        override val size: Int?,
) : DbTimestampColumn<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeColumnNullable<T> {
    override val isNullable = defaultValue == null
}

public class OffsetDateTimeDbTimestampColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val columnName: String?,
        override val size: Int?,
) : DbTimestampColumnNotNull<T, OffsetDateTime>(), OffsetDateTimeColumnNotNull<T>

public class OffsetDateTimeDbTimestampColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> OffsetDateTime?,
        override val columnName: String?,
        override val defaultValue: OffsetDateTime?,
        override val size: Int?,
) : DbTimestampColumn<T, OffsetDateTime>(), OffsetDateTimeColumnNullable<T> {
    override val isNullable = defaultValue == null
}

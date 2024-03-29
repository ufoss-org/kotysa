/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalDateTime

public sealed class DbDateTimeColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    // No decimals
    final override val scale = null

    final override val sqlType = SqlType.DATE_TIME
}


public sealed class DbDateTimeColumnNotNull<T : Any, U : Any> : DbDateTimeColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class LocalDateTimeDbDateTimeColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val columnName: String?,
        override val size: Int?,
) : DbDateTimeColumnNotNull<T, LocalDateTime>(), LocalDateTimeColumnNotNull<T>

public class LocalDateTimeDbDateTimeColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> LocalDateTime?,
        override val columnName: String?,
        override val defaultValue: LocalDateTime?,
        override val size: Int?,
) : DbDateTimeColumn<T, LocalDateTime>(), LocalDateTimeColumnNullable<T> {
    override val isNullable = defaultValue == null
}

public class KotlinxLocalDateTimeDbDateTimeColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val columnName: String?,
        override val size: Int?,
) : DbDateTimeColumnNotNull<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeColumnNotNull<T>

public class KotlinxLocalDateTimeDbDateTimeColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> kotlinx.datetime.LocalDateTime?,
        override val columnName: String?,
        override val defaultValue: kotlinx.datetime.LocalDateTime?,
        override val size: Int?,
) : DbDateTimeColumn<T, kotlinx.datetime.LocalDateTime>(), KotlinxLocalDateTimeColumnNullable<T> {
    override val isNullable = defaultValue == null
}

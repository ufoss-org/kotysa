/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.time.LocalTime

public sealed class DbTimeColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false

    final override val sqlType = SqlType.TIME
}


public sealed class DbTimeColumnNotNull<T : Any, U : Any> : DbTimeColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class LocalTimeDbTimeColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> LocalTime?,
    override val columnName: String?,
    override val size: Int?,
) : DbTimeColumnNotNull<T, LocalTime>(), LocalTimeColumnNotNull<T>

public class LocalTimeDbTimeColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> LocalTime?,
    override val columnName: String?,
    override val defaultValue: LocalTime?,
    override val size: Int?,
) : DbTimeColumn<T, LocalTime>(), LocalTimeColumnNullable<T> {
    override val isNullable = defaultValue == null
}

public class KotlinxLocalTimeDbTimeColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> kotlinx.datetime.LocalTime?,
    override val columnName: String?,
    override val size: Int?,
) : DbTimeColumnNotNull<T, kotlinx.datetime.LocalTime>(), KotlinxLocalTimeColumnNotNull<T>

public class KotlinxLocalTimeDbTimeColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> kotlinx.datetime.LocalTime?,
    override val columnName: String?,
    override val defaultValue: kotlinx.datetime.LocalTime?,
    override val size: Int?,
) : DbTimeColumn<T, kotlinx.datetime.LocalTime>(), KotlinxLocalTimeColumnNullable<T> {
    override val isNullable = defaultValue == null
}

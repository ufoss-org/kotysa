/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbBigIntColumn<T : Any, U : Any> : DbColumn<T, U>() {
    // No size
    final override val size = null

    final override val sqlType = SqlType.BIGINT
}


public sealed class DbBigIntColumnNotNull<T : Any, U : Any> : DbBigIntColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class LongDbBigIntColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Long?,
        override val columnName: String?,
        override val isAutoIncrement: Boolean,
) : DbBigIntColumnNotNull<T, Long>(), LongColumnNotNull<T>

public class LongDbBigIntColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> Long?,
        override val columnName: String?,
        override val defaultValue: Long?,
) : DbBigIntColumn<T, Long>(), LongColumnNullable<T> {
    override val isNullable = defaultValue == null
    // No auto-increment
    override val isAutoIncrement = false
}

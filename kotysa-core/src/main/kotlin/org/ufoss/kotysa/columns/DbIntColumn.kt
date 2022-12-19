/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbIntColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No size nor decimals
    final override val size = null
    final override val scale = null

    final override val sqlType = SqlType.INT
}


public sealed class DbIntColumnNotNull<T : Any, U : Any> : DbIntColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class IntDbIntColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Int?,
        override val columnName: String?,
        override val isAutoIncrement: Boolean,
) : DbIntColumnNotNull<T, Int>(), IntColumnNotNull<T>

public class IntDbIntColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> Int?,
        override val columnName: String?,
        override val defaultValue: Int?,
) : DbIntColumn<T, Int>(), IntColumnNullable<T> {
    override val isNullable = defaultValue == null
    // No auto-increment
    override val isAutoIncrement: Boolean = false
}

public class BooleanDbIntColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Boolean,
        override val columnName: String?,
) : DbIntColumnNotNull<T, Boolean>(), BooleanColumnNotNull<T> {
    // No auto-increment
    override val isAutoIncrement: Boolean = false
}

public class LongDbIntColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Long?,
        override val columnName: String?,
        override val isAutoIncrement: Boolean,
) : DbIntColumnNotNull<T, Long>(), LongColumnNotNull<T>

public class LongDbIntColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> Long?,
        override val columnName: String?,
        override val defaultValue: Long?,
) : DbIntColumn<T, Long>(), LongColumnNullable<T> {
    override val isNullable = defaultValue == null
    // No auto-increment
    override val isAutoIncrement: Boolean = false
}

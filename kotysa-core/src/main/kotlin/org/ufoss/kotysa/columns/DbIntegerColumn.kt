/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbIntegerColumn<T : Any, U : Any> : DbColumn<T, U>() {
    // No size
    final override val size = null

    final override val sqlType = SqlType.INTEGER
}


public sealed class DbIntegerColumnNotNull<T : Any, U : Any> : DbIntegerColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class IntDbIntegerColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Int?,
        override val name: String,
        override val isAutoIncrement: Boolean,
) : DbIntegerColumnNotNull<T, Int>(), IntColumnNotNull<T>

public class IntDbIntegerColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> Int?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: Int?,
) : DbIntegerColumn<T, Int>(), IntColumnNullable<T> {
    // No auto-increment
    override val isAutoIncrement = false
}

public class BooleanDbIntegerColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Boolean,
        override val name: String,
) : DbIntegerColumnNotNull<T, Boolean>(), BooleanColumnNotNull<T> {
    // No auto-increment
    override val isAutoIncrement = false
}

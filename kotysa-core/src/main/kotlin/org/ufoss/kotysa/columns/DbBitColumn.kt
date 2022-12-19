/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.BooleanColumnNotNull
import org.ufoss.kotysa.SqlType

public sealed class DbBitColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No size nor decimals
    final override val size = null
    final override val scale = null

    final override val sqlType = SqlType.BIT
}


public sealed class DbBitColumnNotNull<T : Any, U : Any> : DbBitColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class BooleanDbBitColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Boolean,
        override val columnName: String?,
) : DbBitColumnNotNull<T, Boolean>(), BooleanColumnNotNull<T> {
    // No auto-increment
    override val isAutoIncrement: Boolean = false
}

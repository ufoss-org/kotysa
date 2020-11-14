/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.SqlType

public sealed class BooleanColumnNotNull<T : Any, U : Any> : Column<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
    // No auto-increment
    final override val isAutoIncrement = false
    // No size
    final override val size = null

    final override val sqlType: SqlType = SqlType.BOOLEAN
}

public class BooleanBooleanColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Boolean,
        override val name: String,
) : BooleanColumnNotNull<T, Boolean>(), BooleanFieldColumnNotNull

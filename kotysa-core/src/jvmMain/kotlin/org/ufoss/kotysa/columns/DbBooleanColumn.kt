/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.BooleanColumnNotNull
import org.ufoss.kotysa.SqlType

public sealed class DbBooleanColumnNotNull<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    // No size nor decimals
    final override val size = null
    final override val scale = null

    final override val sqlType: SqlType = SqlType.BOOLEAN
}

public class BooleanDbBooleanColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Boolean,
        override val columnName: String?,
) : DbBooleanColumnNotNull<T, Boolean>(), BooleanColumnNotNull<T>

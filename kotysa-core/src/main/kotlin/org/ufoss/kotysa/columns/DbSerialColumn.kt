/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.DbColumn
import org.ufoss.kotysa.IntColumnNotNull
import org.ufoss.kotysa.SqlType

public sealed class DbSerialColumnNotNull<T : Any, U : Any> : DbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false
    // No size
    final override val size = null
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null

    final override val sqlType = SqlType.SERIAL
}


public class IntDbSerialColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Int?,
        override val name: String,
) : DbSerialColumnNotNull<T, Int>(), IntColumnNotNull<T>

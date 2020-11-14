/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.SqlType

public sealed class SerialColumnNotNull<T : Any, U : Any> : Column<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false
    // No size
    final override val size = null
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null

    final override val sqlType = SqlType.SERIAL
}


public class IntSerialColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Int?,
        override val name: String,
) : SerialColumnNotNull<T, Int>(), IntFieldColumnNotNull

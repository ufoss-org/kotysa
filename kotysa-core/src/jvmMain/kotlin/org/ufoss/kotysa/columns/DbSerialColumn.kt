/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.IntColumnNotNull
import org.ufoss.kotysa.SqlType

public sealed class DbSerialColumnNotNull<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    // No size nor decimals
    final override val size = null
    final override val scale = null
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null

    final override val sqlType = SqlType.SERIAL
}


public class IntDbSerialColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Int?,
        override val columnName: String?,
) : DbSerialColumnNotNull<T, Int>(), IntColumnNotNull<T>

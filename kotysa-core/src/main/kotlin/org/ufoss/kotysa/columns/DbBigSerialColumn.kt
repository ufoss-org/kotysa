/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.LongColumnNotNull
import org.ufoss.kotysa.DbColumn
import org.ufoss.kotysa.SqlType

public sealed class DbBigSerialColumnNotNull<T : Any, U : Any> : DbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false
    // No size
    final override val size = null
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null

    final override val sqlType = SqlType.BIGSERIAL
}


public class LongDbBigSerialColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Long?,
        override val columnName: String?,
) : DbBigSerialColumnNotNull<T, Long>(), LongColumnNotNull<T>

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbBinaryFloatColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    // no size, no scale
    final override val size = null
    final override val scale = null

    final override val sqlType = SqlType.BINARY_FLOAT
}


public sealed class DbBinaryFloatColumnNotNull<T : Any, U : Any> : DbBinaryFloatColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class FloatDbBinaryFloatColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> Float?,
    override val columnName: String?,
) : DbBinaryFloatColumnNotNull<T, Float>(), FloatColumnNotNull<T>

public class FloatDbBinaryFloatColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> Float?,
    override val columnName: String?,
    override val defaultValue: Float?,
) : DbBinaryFloatColumn<T, Float>(), FloatColumnNullable<T> {
    override val isNullable = defaultValue == null
}

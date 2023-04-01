/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbFloatColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false

    final override val sqlType = SqlType.FLOAT
}


public sealed class DbFloatColumnNotNull<T : Any, U : Any> : DbFloatColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class FloatDbFloatColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> Float?,
    override val columnName: String?,
    override val size: Int?,
    override val scale: Int?,
) : DbFloatColumnNotNull<T, Float>(), FloatColumnNotNull<T>

public class FloatDbFloatColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> Float?,
    override val columnName: String?,
    override val defaultValue: Float?,
    override val size: Int?,
    override val scale: Int?,
) : DbFloatColumn<T, Float>(), FloatColumnNullable<T> {
    override val isNullable = defaultValue == null
}

public class DoubleDbFloatColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> Double?,
    override val columnName: String?,
    override val size: Int?,
) : DbFloatColumnNotNull<T, Double>(), DoubleColumnNotNull<T> {
    // No decimals
    override val scale = null
}

public class DoubleDbFloatColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> Double?,
    override val columnName: String?,
    override val defaultValue: Double?,
    override val size: Int?,
) : DbFloatColumn<T, Double>(), DoubleColumnNullable<T> {
    override val isNullable = defaultValue == null
    // No decimals
    override val scale = null
}

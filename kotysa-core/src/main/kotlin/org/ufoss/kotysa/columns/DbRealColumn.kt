/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbRealColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    // No size nor decimals
    final override val size = null
    final override val scale = null

    final override val sqlType = SqlType.REAL
}


public sealed class DbRealColumnNotNull<T : Any, U : Any> : DbRealColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class FloatDbRealColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Float?,
        override val columnName: String?,
) : DbRealColumnNotNull<T, Float>(), FloatColumnNotNull<T>

public class FloatDbRealColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> Float?,
        override val columnName: String?,
        override val defaultValue: Float?,
) : DbRealColumn<T, Float>(), FloatColumnNullable<T> {
    override val isNullable = defaultValue == null
}

public class DoubleDbRealColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> Double?,
    override val columnName: String?,
) : DbRealColumnNotNull<T, Double>(), DoubleColumnNotNull<T>

public class DoubleDbRealColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> Double?,
    override val columnName: String?,
    override val defaultValue: Double?,
) : DbRealColumn<T, Double>(),DoubleColumnNullable<T> {
    override val isNullable = defaultValue == null
}

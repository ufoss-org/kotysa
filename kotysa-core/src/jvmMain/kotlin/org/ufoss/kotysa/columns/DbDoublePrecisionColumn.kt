/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbDoublePrecisionColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false

    final override val sqlType = SqlType.DOUBLE_PRECISION
}


public sealed class DbDoublePrecisionColumnNotNull<T : Any, U : Any> : DbDoublePrecisionColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class DoubleDbDoublePrecisionColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> Double?,
    override val columnName: String?,
    override val size: Int?,
    override val scale: Int?,
) : DbDoublePrecisionColumnNotNull<T, Double>(), DoubleColumnNotNull<T>

public class DoubleDbDoublePrecisionColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> Double?,
    override val columnName: String?,
    override val defaultValue: Double?,
    override val size: Int?,
    override val scale: Int?,
) : DbDoublePrecisionColumn<T, Double>(), DoubleColumnNullable<T> {
    override val isNullable = defaultValue == null
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbBinaryDoubleColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    // no size, no scale
    final override val size = null
    final override val scale = null

    final override val sqlType = SqlType.BINARY_DOUBLE
}


public sealed class DbBinaryDoubleColumnNotNull<T : Any, U : Any> : DbBinaryDoubleColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class DoubleDbBinaryDoubleColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> Double?,
    override val columnName: String?,
) : DbBinaryDoubleColumnNotNull<T, Double>(), DoubleColumnNotNull<T>

public class DoubleDbBinaryDoubleColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> Double?,
    override val columnName: String?,
    override val defaultValue: Double?,
) : DbBinaryDoubleColumn<T, Double>(), DoubleColumnNullable<T> {
    override val isNullable = defaultValue == null
}

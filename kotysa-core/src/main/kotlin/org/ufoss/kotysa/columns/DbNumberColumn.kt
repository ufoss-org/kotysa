/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.math.BigDecimal

public sealed class DbNumberColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false

    final override val sqlType = SqlType.NUMBER
}


public sealed class DbNumberColumnNotNull<T : Any, U : Any> : DbNumberColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class IntDbNumberColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> Int?,
    override val columnName: String?,
    override val size: Int,
) : DbNumberColumnNotNull<T, Int>(), IntColumnNotNull<T> {
    override val scale = null
}

public class IntDbNumberColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> Int?,
    override val columnName: String?,
    override val defaultValue: Int?,
    override val size: Int,
) : DbNumberColumn<T, Int>(), IntColumnNullable<T> {
    override val isNullable = defaultValue == null
    override val scale = null
}

public class BooleanDbNumberColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> Boolean,
    override val columnName: String?,
) : DbNumberColumnNotNull<T, Boolean>(), BooleanColumnNotNull<T> {
    override val size = 1
    override val scale = null
}

public class LongDbNumberColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> Long?,
    override val columnName: String?,
    override val size: Int,
) : DbNumberColumnNotNull<T, Long>(), LongColumnNotNull<T> {
    override val scale = null
}

public class LongDbNumberColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> Long?,
    override val columnName: String?,
    override val defaultValue: Long?,
    override val size: Int,
) : DbNumberColumn<T, Long>(), LongColumnNullable<T> {
    override val isNullable = defaultValue == null
    override val scale = null
}

public class BigDecimalDbNumberColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> BigDecimal?,
    override val columnName: String?,
    override val size: Int,
    override val scale: Int,
) : DbNumberColumnNotNull<T, BigDecimal>(), BigDecimalColumnNotNull<T>

public class BigDecimalDbNumberColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> BigDecimal?,
    override val columnName: String?,
    override val defaultValue: BigDecimal?,
    override val size: Int,
    override val scale: Int,
) : DbNumberColumn<T, BigDecimal>(), BigDecimalColumnNullable<T> {
    override val isNullable = defaultValue == null
}

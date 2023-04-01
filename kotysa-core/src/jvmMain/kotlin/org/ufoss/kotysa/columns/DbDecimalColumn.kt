/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.math.BigDecimal

public sealed class DbDecimalColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false

    final override val sqlType = SqlType.DECIMAL
}


public sealed class DbDecimalColumnNotNull<T : Any, U : Any> : DbDecimalColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class BigDecimalDbDecimalColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> BigDecimal?,
    override val columnName: String?,
    override val size: Int,
    override val scale: Int,
) : DbDecimalColumnNotNull<T, BigDecimal>(), BigDecimalColumnNotNull<T>

public class BigDecimalDbDecimalColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> BigDecimal?,
    override val columnName: String?,
    override val defaultValue: BigDecimal?,
    override val size: Int,
    override val scale: Int,
) : DbDecimalColumn<T, BigDecimal>(), BigDecimalColumnNullable<T> {
    override val isNullable = defaultValue == null
}

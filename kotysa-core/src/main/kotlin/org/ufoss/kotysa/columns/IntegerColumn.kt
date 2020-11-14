/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.SqlType

public sealed class IntegerColumn<T : Any, U : Any> : Column<T, U>() {
    // No size
    final override val size = null

    final override val sqlType = SqlType.INTEGER
}


public sealed class IntegerColumnNotNull<T : Any, U : Any> : IntegerColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class IntIntegerColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Int?,
        override val name: String,
        override val isAutoIncrement: Boolean,
) : IntegerColumnNotNull<T, Int>(), IntFieldColumnNotNull

public class IntIntegerColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> Int?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: Int?,
) : IntegerColumn<T, Int>(), IntFieldColumnNullable {
    // No auto-increment
    override val isAutoIncrement = false
}

public class BooleanIntegerColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> Boolean,
        override val name: String,
) : IntegerColumnNotNull<T, Boolean>(), BooleanFieldColumnNotNull {
    // No auto-increment
    override val isAutoIncrement = false
}

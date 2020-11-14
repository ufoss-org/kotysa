/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.SqlType

public sealed class VarcharColumn<T : Any, U : Any> : Column<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false

    final override val sqlType = SqlType.VARCHAR
}


public sealed class VarcharColumnNotNull<T : Any, U : Any> : VarcharColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class StringVarcharColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> String?,
        override val name: String,
        override val size: Int?,
) : VarcharColumnNotNull<T, String>(), StringFieldColumnNotNull

public class StringVarcharColNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> String?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: String?,
        override val size: Int?,
) : VarcharColumn<T, String>(), StringFieldColumnNullable

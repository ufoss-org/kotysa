/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.DbColumn
import org.ufoss.kotysa.SqlType
import org.ufoss.kotysa.StringColumnNotNull
import org.ufoss.kotysa.StringColumnNullable

public sealed class DbVarcharColumn<T : Any, U : Any> : DbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false

    final override val sqlType = SqlType.VARCHAR
}


public sealed class DbVarcharColumnNotNull<T : Any, U : Any> : DbVarcharColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class StringDbVarcharColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> String?,
        override val name: String?,
        override val size: Int?,
) : DbVarcharColumnNotNull<T, String>(), StringColumnNotNull<T>

public class StringDbVarcharColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> String?,
        override val name: String?,
        override val isNullable: Boolean,
        override val defaultValue: String?,
        override val size: Int?,
) : DbVarcharColumn<T, String>(), StringColumnNullable<T>

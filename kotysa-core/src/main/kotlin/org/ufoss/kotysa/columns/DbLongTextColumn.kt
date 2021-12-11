/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbLongTextColumn<T : Any, U : Any> : DbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    // No size
    final override val size = null

    final override val sqlType = SqlType.LONGTEXT
}

public sealed class DbLongTextColumnNotNull<T : Any, U : Any> : DbLongTextColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class StringDbLongTextColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> String?,
    override val columnName: String?,
) : DbLongTextColumnNotNull<T, String>(), StringColumnNotNull<T>

public class StringDbLongTextColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> String?,
    override val columnName: String?,
    override val defaultValue: String?,
) : DbLongTextColumn<T, String>(), StringColumnNullable<T> {
    override val isNullable = defaultValue == null
}

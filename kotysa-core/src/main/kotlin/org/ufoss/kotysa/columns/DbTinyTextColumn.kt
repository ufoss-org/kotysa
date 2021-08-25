/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbTinyTextColumn<T : Any, U : Any> : DbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false
    // No size
    final override val size = null

    final override val sqlType = SqlType.TINYTEXT
}

public sealed class DbTinyTextColumnNotNull<T : Any, U : Any> : DbTinyTextColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class StringDbTinyTextColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> String?,
    override val columnName: String?,
) : DbTinyTextColumnNotNull<T, String>(), StringColumnNotNull<T>

public class StringDbTinyTextColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> String?,
    override val columnName: String?,
    override val defaultValue: String?,
) : DbTinyTextColumn<T, String>(), StringColumnNullable<T> {
    override val isNullable = defaultValue == null
}
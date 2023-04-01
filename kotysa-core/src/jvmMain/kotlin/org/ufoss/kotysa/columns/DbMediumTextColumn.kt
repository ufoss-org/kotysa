/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbMediumTextColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    // No size nor decimals
    final override val size = null
    final override val scale = null

    final override val sqlType = SqlType.MEDIUMTEXT
}

public sealed class DbMediumTextColumnNotNull<T : Any, U : Any> : DbMediumTextColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class StringDbMediumTextColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> String?,
    override val columnName: String?,
) : DbMediumTextColumnNotNull<T, String>(), StringColumnNotNull<T>

public class StringDbMediumTextColumnNullable<T : Any> internal constructor(
    override val entityGetter: (T) -> String?,
    override val columnName: String?,
    override val defaultValue: String?,
) : DbMediumTextColumn<T, String>(), StringColumnNullable<T> {
    override val isNullable = defaultValue == null
}

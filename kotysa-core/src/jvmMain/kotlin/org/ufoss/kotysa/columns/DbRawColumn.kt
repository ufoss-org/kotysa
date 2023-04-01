/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbRawColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No decimals
    final override val scale = null
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    override val defaultValue: U? = null

    final override val sqlType = SqlType.RAW
}


public sealed class DbRawColumnNotNull<T : Any, U : Any> : DbRawColumn<T, U>() {
    // Not null
    final override val isNullable = false
}

public class ByteArrayDbRawColumnNotNull<T : Any> internal constructor(
    override val entityGetter: (T) -> ByteArray?,
    override val columnName: String?,
    override val size: Int,
) : DbRawColumnNotNull<T, ByteArray>(), ByteArrayColumnNotNull<T>

public class ByteArrayDbRawColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> ByteArray?,
        override val columnName: String?,
        override val size: Int,
) : DbRawColumn<T, ByteArray>(), ByteArrayColumnNullable<T> {
    override val isNullable = true
}

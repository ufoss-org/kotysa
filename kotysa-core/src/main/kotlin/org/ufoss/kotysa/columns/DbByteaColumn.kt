/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

/**
 * Postgresql specific type bytea
 */
public sealed class DbByteaColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No size
    final override val size = null
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    override val defaultValue: U? = null

    final override val sqlType = SqlType.BYTEA
}


public sealed class DbByteaColumnNotNull<T : Any, U : Any> : DbByteaColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
}

public class ByteArrayDbByteaColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> ByteArray?,
        override val columnName: String?,
) : DbByteaColumnNotNull<T, ByteArray>(), ByteArrayColumnNotNull<T>

public class ByteArrayDbByteaColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> ByteArray?,
        override val columnName: String?,
) : DbByteaColumn<T, ByteArray>(), ByteArrayColumnNullable<T> {
    override val isNullable = true
}

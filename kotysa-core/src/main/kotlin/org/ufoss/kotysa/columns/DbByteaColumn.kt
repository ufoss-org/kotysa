/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

/**
 * Postgresql specific type bytea
 */
public sealed class DbByteaColumn<T : Any, U : Any> : DbColumn<T, U>() {
    // No size
    final override val size = null
    // No auto-increment
    final override val isAutoIncrement: Boolean = false

    final override val sqlType = SqlType.BYTEA
}


public sealed class DbByteaColumnNotNull<T : Any, U : Any> : DbByteaColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class ByteArrayDbByteaColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> ByteArray?,
        override val columnName: String?,
) : DbByteaColumnNotNull<T, ByteArray>(), ByteArrayColumnNotNull<T>

public class ByteArrayDbByteaColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> ByteArray?,
        override val columnName: String?,
        override val defaultValue: ByteArray?,
) : DbByteaColumn<T, ByteArray>(), ByteArrayColumnNullable<T> {
    override val isNullable = defaultValue == null
}

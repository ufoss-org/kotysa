/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbBinaryColumn<T : Any, U : Any> : DbColumn<T, U>() {
    // No size
    final override val size = null
    // No auto-increment
    final override val isAutoIncrement: Boolean = false

    final override val sqlType = SqlType.BINARY
}


public sealed class DbBinaryColumnNotNull<T : Any, U : Any> : DbBinaryColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class ByteArrayDbBinaryColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> ByteArray?,
        override val columnName: String?,
) : DbBinaryColumnNotNull<T, ByteArray>(), ByteArrayColumnNotNull<T>

public class ByteArrayDbBinaryColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> ByteArray?,
        override val columnName: String?,
        override val defaultValue: ByteArray?,
) : DbBinaryColumn<T, ByteArray>(), ByteArrayColumnNullable<T> {
    override val isNullable = defaultValue == null
}

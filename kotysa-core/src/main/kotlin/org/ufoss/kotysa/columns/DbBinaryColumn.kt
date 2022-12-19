/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbBinaryColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No size nor decimals
    final override val size = null
    final override val scale = null
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    override val defaultValue: U? = null

    final override val sqlType = SqlType.BINARY
}


public sealed class DbBinaryColumnNotNull<T : Any, U : Any> : DbBinaryColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
}

public class ByteArrayDbBinaryColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> ByteArray?,
        override val columnName: String?,
) : DbBinaryColumnNotNull<T, ByteArray>(), ByteArrayColumnNotNull<T>

public class ByteArrayDbBinaryColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> ByteArray?,
        override val columnName: String?,
) : DbBinaryColumn<T, ByteArray>(), ByteArrayColumnNullable<T> {
    override val isNullable = defaultValue == null
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbBlobColumn<T : Any, U : Any> : DbColumn<T, U>() {
    // No size
    final override val size = null
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    override val defaultValue: U? = null

    final override val sqlType = SqlType.BLOB
}


public sealed class DbBlobColumnNotNull<T : Any, U : Any> : DbBlobColumn<T, U>() {
    // Not null
    final override val isNullable = false
}

public class ByteArrayDbBlobColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> ByteArray?,
        override val columnName: String?,
) : DbBlobColumnNotNull<T, ByteArray>(), ByteArrayColumnNotNull<T>

public class ByteArrayDbBlobColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> ByteArray?,
        override val columnName: String?,
) : DbBlobColumn<T, ByteArray>(), ByteArrayColumnNullable<T> {
    override val isNullable = true
}
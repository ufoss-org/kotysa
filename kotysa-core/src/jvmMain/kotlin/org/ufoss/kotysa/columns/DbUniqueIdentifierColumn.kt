/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.util.*

public sealed class DbUniqueIdentifierColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    // No size nor decimals
    final override val size = null
    final override val scale = null

    final override val sqlType = SqlType.UNIQUEIDENTIFIER
}


public sealed class DbUniqueIdentifierColumnNotNull<T : Any, U : Any> : DbUniqueIdentifierColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class UuidDbUniqueIdentifierColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> UUID?,
        override val columnName: String?,
) : DbUniqueIdentifierColumnNotNull<T, UUID>(), UuidColumnNotNull<T>


public class UuidDbUniqueIdentifierColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> UUID?,
        override val columnName: String?,
        override val defaultValue: UUID?,
) : DbUniqueIdentifierColumn<T, UUID>(), UuidColumnNullable<T> {
    override val isNullable = defaultValue == null
}

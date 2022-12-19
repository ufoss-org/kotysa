/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType
import org.ufoss.kotysa.UuidColumnNotNull
import org.ufoss.kotysa.UuidColumnNullable
import java.util.*

public sealed class DbUuidColumn<T : Any, U : Any> : AbstractDbColumn<T, U>() {
    // No auto-increment
    final override val isAutoIncrement: Boolean = false
    // No size nor decimals
    final override val size = null
    final override val scale = null

    final override val sqlType = SqlType.UUID
}


public sealed class DbUuidColumnNotNull<T : Any, U : Any> : DbUuidColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class UuidDbUuidColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> UUID?,
        override val columnName: String?,
) : DbUuidColumnNotNull<T, UUID>(), UuidColumnNotNull<T>


public class UuidDbUuidColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> UUID?,
        override val columnName: String?,
        override val defaultValue: UUID?,
) : DbUuidColumn<T, UUID>(), UuidColumnNullable<T> {
    override val isNullable = defaultValue == null
}

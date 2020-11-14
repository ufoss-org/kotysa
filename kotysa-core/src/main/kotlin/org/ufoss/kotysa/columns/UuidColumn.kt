/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.SqlType
import java.util.*

public sealed class UuidColumn<T : Any, U : Any> : Column<T, U>() {
    // No auto-increment
    final override val isAutoIncrement = false
    // No size
    final override val size = null

    final override val sqlType = SqlType.UUID
}


public sealed class UuidColumnNotNull<T : Any, U : Any> : UuidColumn<T, U>() {
    // Not null
    final override val isNullable: Boolean = false
    final override val defaultValue: U? = null
}

public class UuidUuidColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> UUID?,
        override val name: String,
) : UuidColumnNotNull<T, UUID>(), UuidFieldColumnNotNull


public class UuidUuidColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> UUID?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: UUID?,
) : UuidColumn<T, UUID>(), UuidFieldColumnNullable

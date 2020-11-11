/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.SqlType
import java.util.*

internal interface UuidColumn<T : Any, U : Any> : Column<T, U>, NoAutoIncrement<T, U>, NoSize<T, U> {
    override val sqlType get() = SqlType.UUID
}


public abstract class UuidColumnNotNull<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), UuidColumn<T, U>, ColumnNotNull<T, U>


public abstract class UuidColumnNullable<T : Any, U : Any> protected constructor()
    : AbstractColumn<T, U>(), UuidColumn<T, U>, ColumnNullable<T, U>

public class UuidUuidColumnNotNull<T : Any> internal constructor(
        override val entityGetter: (T) -> UUID?,
        override val name: String,
) : UuidColumnNotNull<T, UUID>(), UuidFieldColumnNotNull


public class UuidUuidColumnNullable<T : Any> internal constructor(
        override val entityGetter: (T) -> UUID?,
        override val name: String,
        override val isNullable: Boolean,
        override val defaultValue: UUID?,
) : UuidColumnNullable<T, UUID>(), UuidFieldColumnNullable

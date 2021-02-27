/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.util.*

public sealed class DbUuidColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class UuidDbUuidColumnNotNullDsl<T : Any> internal constructor(
        private val init: (UuidDbUuidColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> UUID
) : DbUuidColumnDsl<T, UUID>(entityGetter) {

    internal fun initialize(): UuidDbUuidColumnNotNull<T> {
        init?.invoke(this)
        return UuidDbUuidColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class UuidDbUuidColumnNullableDsl<T : Any> internal constructor(
        private val init: (UuidDbUuidColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> UUID?
) : DbUuidColumnDsl<T, UUID>(entityGetter) {

    public var defaultValue: UUID? = null

    internal fun initialize(): UuidDbUuidColumnNullable<T> {
        init?.invoke(this)
        return UuidDbUuidColumnNullable(entityGetter, columnName, defaultValue)
    }
}

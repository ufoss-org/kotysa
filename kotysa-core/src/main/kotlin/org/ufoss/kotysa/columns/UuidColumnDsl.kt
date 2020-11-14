/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*
import java.util.*

public sealed class UuidColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class UuidUuidColumnNotNullDsl<T : Any> internal constructor(
        private val init: (UuidUuidColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> UUID
) : UuidColumnDsl<T, UUID>(entityGetter) {

    internal fun initialize(): UuidUuidColumnNotNull<T> {
        init?.invoke(this)
        return UuidUuidColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class UuidUuidColumnNullableDsl<T : Any> internal constructor(
        private val init: (UuidUuidColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> UUID?
) : UuidColumnDsl<T, UUID>(entityGetter) {

    public var defaultValue: UUID? = null

    internal fun initialize(): UuidUuidColumnNullable<T> {
        init?.invoke(this)
        return UuidUuidColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public abstract class UuidColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class UuidColumnNotNullDsl<T : Any, U : Any> internal constructor(
        private val init: (UuidColumnNotNullDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U
) : UuidColumnDsl<T, U>(entityGetter) {

    internal fun initialize(): UuidColumnNotNull<T, U> {
        init?.invoke(this)
        return UuidColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class UuidColumnNullableDsl<T : Any, U : Any> internal constructor(
        private val init: (UuidColumnNullableDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U?
) : UuidColumnDsl<T, U>(entityGetter) {

    public var defaultValue: U? = null

    internal fun initialize(): UuidColumnNullable<T, U> {
        init?.invoke(this)
        return UuidColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

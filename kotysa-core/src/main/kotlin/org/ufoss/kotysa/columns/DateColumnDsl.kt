/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public abstract class DateColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class DateColumnNotNullDsl<T : Any, U : Any> internal constructor(
        private val init: (DateColumnNotNullDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U
) : DateColumnDsl<T, U>(entityGetter) {

    internal fun initialize(): DateColumnNotNull<T, U> {
        init?.invoke(this)
        return DateColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class DateColumnNullableDsl<T : Any, U : Any> internal constructor(
        private val init: (DateColumnNullableDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U?
) : DateColumnDsl<T, U>(entityGetter) {

    public var defaultValue: U? = null

    internal fun initialize(): DateColumnNullable<T, U> {
        init?.invoke(this)
        return DateColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

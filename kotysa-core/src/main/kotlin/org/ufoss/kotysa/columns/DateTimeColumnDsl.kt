/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public abstract class DateTimeColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var fractionalSecondsPart: Int? = null
}

@KotysaMarker
public class DateTimeColumnNotNullDsl<T : Any, U : Any> internal constructor(
        private val init: (DateTimeColumnNotNullDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U
) : DateTimeColumnDsl<T, U>(entityGetter) {

    internal fun initialize(): DateTimeColumnNotNull<T, U> {
        init?.invoke(this)
        return DateTimeColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class DateTimeColumnNullableDsl<T : Any, U : Any> internal constructor(
        private val init: (DateTimeColumnNullableDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U?
) : DateTimeColumnDsl<T, U>(entityGetter) {

    public var defaultValue: U? = null

    internal fun initialize(): DateTimeColumnNullable<T, U> {
        init?.invoke(this)
        return DateTimeColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue, fractionalSecondsPart)
    }
}

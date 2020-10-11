/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public abstract class TimestampColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var fractionalSecondsPart: Int? = null
}

@KotysaMarker
public class TimestampColumnNotNullDsl<T : Any, U : Any> internal constructor(
        private val init: (TimestampColumnNotNullDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U
) : TimestampColumnDsl<T, U>(entityGetter) {

    internal fun initialize(): TimestampColumnNotNull<T, U> {
        init?.invoke(this)
        return TimestampColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class TimestampColumnNullableDsl<T : Any, U : Any> internal constructor(
        private val init: (TimestampColumnNullableDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U?
) : TimestampColumnDsl<T, U>(entityGetter) {

    public var defaultValue: U? = null

    internal fun initialize(): TimestampColumnNullable<T, U> {
        init?.invoke(this)
        return TimestampColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue, fractionalSecondsPart)
    }
}

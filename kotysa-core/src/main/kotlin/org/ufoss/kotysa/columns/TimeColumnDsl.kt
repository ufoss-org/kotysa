/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public abstract class TimeColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter) {
    public var fractionalSecondsPart: Int? = null
}

@KotysaMarker
public class TimeColumnNotNullDsl<T : Any, U : Any> internal constructor(
        private val init: (TimeColumnNotNullDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U
) : TimeColumnDsl<T, U>(entityGetter) {

    internal fun initialize(): TimeColumnNotNull<T, U> {
        init?.invoke(this)
        return TimeColumnNotNull(entityGetter, columnName, fractionalSecondsPart)
    }
}

@KotysaMarker
public class TimeColumnNullableDsl<T : Any, U : Any> internal constructor(
        private val init: (TimeColumnNullableDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U?
) : TimeColumnDsl<T, U>(entityGetter) {

    public var defaultValue: U? = null

    internal fun initialize(): TimeColumnNullable<T, U> {
        init?.invoke(this)
        return TimeColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue, fractionalSecondsPart)
    }
}

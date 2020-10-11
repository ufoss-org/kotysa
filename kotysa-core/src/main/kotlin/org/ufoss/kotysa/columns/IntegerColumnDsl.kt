/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public abstract class IntegerColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class IntegerColumnNotNullDsl<T : Any, U : Any> internal constructor(
        private val init: (IntegerColumnNotNullDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U
) : IntegerColumnDsl<T, U>(entityGetter) {

    internal fun initialize(): IntegerColumnNotNull<T, U> {
        init?.invoke(this)
        return IntegerColumnNotNull(entityGetter, columnName, false)
    }
}

@KotysaMarker
public class IntegerColumnNullableDsl<T : Any, U : Any> internal constructor(
        private val init: (IntegerColumnNullableDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U?
) : IntegerColumnDsl<T, U>(entityGetter) {

    public var defaultValue: U? = null

    internal fun initialize(): IntegerColumnNullable<T, U> {
        init?.invoke(this)
        return IntegerColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class IntegerAutoIncrementColumnDsl<T : Any, U : Any> internal constructor(
        private val init: (IntegerAutoIncrementColumnDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U?
) : IntegerColumnDsl<T, U>(entityGetter) {

    internal fun initialize(): IntegerColumnNotNull<T, U> {
        init?.invoke(this)
        return IntegerColumnNotNull(entityGetter, columnName, true)
    }
}

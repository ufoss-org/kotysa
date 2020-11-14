/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class IntegerColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class IntIntegerColumnNotNullDsl<T : Any> internal constructor(
        private val init: (IntIntegerColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> Int
) : IntegerColumnDsl<T, Int>(entityGetter) {

    internal fun initialize(): IntIntegerColumnNotNull<T> {
        init?.invoke(this)
        return IntIntegerColumnNotNull(entityGetter, columnName, false)
    }
}

@KotysaMarker
public class IntIntegerColumnNullableDsl<T : Any> internal constructor(
        private val init: (IntIntegerColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> Int?
) : IntegerColumnDsl<T, Int>(entityGetter) {

    public var defaultValue: Int? = null

    internal fun initialize(): IntIntegerColumnNullable<T> {
        init?.invoke(this)
        return IntIntegerColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

@KotysaMarker
public class IntIntegerAutoIncrementColumnDsl<T : Any> internal constructor(
        private val init: (IntIntegerAutoIncrementColumnDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> Int?
) : IntegerColumnDsl<T, Int>(entityGetter) {

    internal fun initialize(): IntIntegerColumnNotNull<T> {
        init?.invoke(this)
        return IntIntegerColumnNotNull(entityGetter, columnName, true)
    }
}

@KotysaMarker
public class BooleanIntegerColumnNotNullDsl<T : Any> internal constructor(
        private val init: (BooleanIntegerColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> Boolean
) : IntegerColumnDsl<T, Boolean>(entityGetter) {

    internal fun initialize(): BooleanIntegerColumnNotNull<T> {
        init?.invoke(this)
        return BooleanIntegerColumnNotNull(entityGetter, columnName)
    }
}

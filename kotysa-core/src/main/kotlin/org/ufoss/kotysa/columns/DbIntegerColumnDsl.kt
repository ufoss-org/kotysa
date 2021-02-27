/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public sealed class DbIntegerColumnDsl<T : Any, U : Any>(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class IntDbIntegerColumnNotNullDsl<T : Any> internal constructor(
        private val init: (IntDbIntegerColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> Int
) : DbIntegerColumnDsl<T, Int>(entityGetter) {

    internal fun initialize(): IntDbIntegerColumnNotNull<T> {
        init?.invoke(this)
        return IntDbIntegerColumnNotNull(entityGetter, columnName, false)
    }
}

@KotysaMarker
public class IntDbIntegerColumnNullableDsl<T : Any> internal constructor(
        private val init: (IntDbIntegerColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> Int?
) : DbIntegerColumnDsl<T, Int>(entityGetter) {

    public var defaultValue: Int? = null

    internal fun initialize(): IntDbIntegerColumnNullable<T> {
        init?.invoke(this)
        return IntDbIntegerColumnNullable(entityGetter, columnName, defaultValue)
    }
}

@KotysaMarker
public class IntDbIntegerAutoIncrementColumnDsl<T : Any> internal constructor(
        private val init: (IntDbIntegerAutoIncrementColumnDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> Int?
) : DbIntegerColumnDsl<T, Int>(entityGetter) {

    internal fun initialize(): IntDbIntegerColumnNotNull<T> {
        init?.invoke(this)
        return IntDbIntegerColumnNotNull(entityGetter, columnName, true)
    }
}

@KotysaMarker
public class BooleanDbIntegerColumnNotNullDsl<T : Any> internal constructor(
        private val init: (BooleanDbIntegerColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> Boolean
) : DbIntegerColumnDsl<T, Boolean>(entityGetter) {

    internal fun initialize(): BooleanDbIntegerColumnNotNull<T> {
        init?.invoke(this)
        return BooleanDbIntegerColumnNotNull(entityGetter, columnName)
    }
}

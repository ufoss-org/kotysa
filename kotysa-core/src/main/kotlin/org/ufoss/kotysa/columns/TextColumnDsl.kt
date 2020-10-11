/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public abstract class TextColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class TextColumnNotNullDsl<T : Any, U : Any> internal constructor(
        private val init: (TextColumnNotNullDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U
) : TextColumnDsl<T, U>(entityGetter) {

    internal fun initialize(): TextColumnNotNull<T, U> {
        init?.invoke(this)
        return TextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class TextColumnNullableDsl<T : Any, U : Any> internal constructor(
        private val init: (TextColumnNullableDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U?
) : TextColumnDsl<T, U>(entityGetter) {

    public var defaultValue: U? = null

    internal fun initialize(): TextColumnNullable<T, U> {
        init?.invoke(this)
        return TextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

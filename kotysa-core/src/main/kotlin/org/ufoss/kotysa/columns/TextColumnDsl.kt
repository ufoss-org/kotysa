/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

public abstract class TextColumnDsl<T : Any, U : Any> protected constructor(
        entityGetter: (T) -> U?
) : ColumnDescriptionDsl<T, U>(entityGetter)

@KotysaMarker
public class StringTextColumnNotNullDsl<T : Any> internal constructor(
        private val init: (StringTextColumnNotNullDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> String
) : TextColumnDsl<T, String>(entityGetter) {

    internal fun initialize(): StringTextColumnNotNull<T> {
        init?.invoke(this)
        return StringTextColumnNotNull(entityGetter, columnName)
    }
}

@KotysaMarker
public class StringTextColumnNullableDsl<T : Any> internal constructor(
        private val init: (StringTextColumnNullableDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> String?
) : TextColumnDsl<T, String>(entityGetter) {

    public var defaultValue: String? = null

    internal fun initialize(): StringTextColumnNullable<T> {
        init?.invoke(this)
        return StringTextColumnNullable(entityGetter, columnName, defaultValue == null, defaultValue)
    }
}

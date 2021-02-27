/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

@KotysaMarker
public class DbBooleanColumnDsl<T : Any> internal constructor(
        private val init: (DbBooleanColumnDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> Boolean
) : ColumnDescriptionDsl<T, Boolean>(entityGetter) {

    internal fun initialize(): BooleanDbBooleanColumnNotNull<T> {
        init?.invoke(this)
        return BooleanDbBooleanColumnNotNull(entityGetter, columnName)
    }
}

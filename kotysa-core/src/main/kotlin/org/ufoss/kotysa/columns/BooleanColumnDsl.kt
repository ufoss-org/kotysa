/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

@KotysaMarker
public class BooleanColumnDsl<T : Any> internal constructor(
        private val init: (BooleanColumnDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> Boolean
) : ColumnDescriptionDsl<T, Boolean>(entityGetter) {

    internal fun initialize(): BooleanBooleanColumnNotNull<T> {
        init?.invoke(this)
        return BooleanBooleanColumnNotNull(entityGetter, columnName)
    }
}

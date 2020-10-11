/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

@KotysaMarker
public class BooleanColumnDsl<T : Any, U : Any> internal constructor(
        private val init: (BooleanColumnDsl<T, U>.() -> Unit)?,
        private val entityGetter: (T) -> U
) : ColumnDescriptionDsl<T, U>(entityGetter) {

    internal fun initialize(): BooleanColumnNotNull<T, U> {
        init?.invoke(this)
        return BooleanColumnNotNull(entityGetter, columnName)
    }
}

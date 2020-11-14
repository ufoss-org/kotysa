/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.columns

import org.ufoss.kotysa.*

@KotysaMarker
public class SerialColumnDsl<T : Any> internal constructor(
        private val init: (SerialColumnDsl<T>.() -> Unit)?,
        private val entityGetter: (T) -> Int?
) : ColumnDescriptionDsl<T, Int>(entityGetter) {

    internal fun initialize(): IntSerialColumnNotNull<T> {
        init?.invoke(this)
        return IntSerialColumnNotNull(entityGetter, columnName)
    }
}

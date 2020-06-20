/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

@KotysaMarker
public class PrimaryKeyDsl<T : Any> internal constructor(
        private val init: PrimaryKeyDsl<T>.(TableColumnPropertyProvider) -> PrimaryKey<T>
) : AbstractTableColumnPropertyProvider() {

    internal fun initialize() = init(this)
}

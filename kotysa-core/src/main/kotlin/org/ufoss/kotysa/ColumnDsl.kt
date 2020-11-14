/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


@KotysaMarker
public abstract class ColumnDsl<T : Any, U : DbColumn<T, *>, V : ColumnDsl<T, U, V>> internal constructor(
        private val init: V.(TableColumnPropertyProvider<T>) -> U
) : AbstractTableColumnPropertyProvider<T>() {

    internal fun <U> initialize(initialize: V) = init(initialize, initialize)
}

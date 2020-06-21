/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


@KotysaMarker
public abstract class ColumnDsl<T : Any, U : ColumnDsl<T, U>> internal constructor(
        private val init: U.(TableColumnPropertyProvider<T>) -> ColumnBuilder<*, T, *, *>
) : AbstractTableColumnPropertyProvider<T>() {

    @Suppress("UNCHECKED_CAST")
    internal fun <V : Column<T, *>> initialize(initialize: U): V {
        val columnBuilder = init(initialize, initialize)
        if (!columnBuilder.isColumnNameInitialized) {
            columnBuilder.props.columnName = columnBuilder.props.entityGetter.toCallable().name
        }
        return columnBuilder.build() as V
    }
}

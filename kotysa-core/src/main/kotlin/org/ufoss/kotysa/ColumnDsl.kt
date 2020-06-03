/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa


@KotysaMarker
public abstract class ColumnDsl<T : Any, U : ColumnDsl<T, U>> internal constructor(
        private val init: U.(TableColumnPropertyProvider) -> ColumnBuilder<*, T, *>
) {

    @Suppress("UNCHECKED_CAST")
    internal fun initialize(initialize: U, tableColumnPropertyProvider: TableColumnPropertyProvider): Column<T, *> {
        val columnBuilder = init(initialize, tableColumnPropertyProvider)
        if (!columnBuilder.isColumnNameInitialized) {
            columnBuilder.props.columnName = columnBuilder.props.entityGetter.toCallable().name
        }
        return columnBuilder.build()
    }
}

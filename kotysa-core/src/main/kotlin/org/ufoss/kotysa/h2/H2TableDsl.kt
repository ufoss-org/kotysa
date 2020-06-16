/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.h2

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.ColumnBuilder
import org.ufoss.kotysa.TableColumnPropertyProvider
import org.ufoss.kotysa.TableDsl
import kotlin.reflect.KClass


public class H2TableDsl<T : Any>(
        init: H2TableDsl<T>.() -> Unit,
        tableClass: KClass<T>
) : TableDsl<T, H2TableDsl<T>>(init, tableClass) {

    /**
     * Declare a Column, supported types follow : [H2 Data types](http://h2database.com/html/datatypes.html)
     */
    public fun column(dsl: H2ColumnDsl<T>.(TableColumnPropertyProvider) -> ColumnBuilder<*, T, *>): Column<T, *> {
        val columnDsl = H2ColumnDsl(dsl)
        val column = columnDsl.initialize(columnDsl)
        addColumn(column)
        return column
    }
}

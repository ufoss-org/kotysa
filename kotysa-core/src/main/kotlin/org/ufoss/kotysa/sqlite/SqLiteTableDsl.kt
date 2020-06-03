/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sqlite

import org.ufoss.kotysa.ColumnBuilder
import org.ufoss.kotysa.TableColumnPropertyProvider
import org.ufoss.kotysa.TableDsl
import kotlin.reflect.KClass


public class SqLiteTableDsl<T : Any>(
        init: SqLiteTableDsl<T>.(TableColumnPropertyProvider) -> Unit,
        tableClass: KClass<T>
) : TableDsl<T, SqLiteTableDsl<T>>(init, tableClass) {

    /**
     * Declare a Column, supported types follow : [SqLite Data types](https://www.sqlite.org/datatype3.html)
     */
    public fun column(dsl: SqLiteColumnDsl<T>.(TableColumnPropertyProvider) -> ColumnBuilder<*, T, *>) {
        val columnDsl = SqLiteColumnDsl(dsl)
        val column = columnDsl.initialize(columnDsl, this)
        addColumn(column)
    }
}

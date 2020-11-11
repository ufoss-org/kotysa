/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.mysql

import org.ufoss.kotysa.columns.Column
import org.ufoss.kotysa.TableColumnPropertyProvider
import org.ufoss.kotysa.TableDsl
import kotlin.reflect.KClass


public class MysqlTableDsl<T : Any>(
        init: MysqlTableDsl<T>.() -> Unit,
        tableClass: KClass<T>
) : TableDsl<T, MysqlTableDsl<T>>(init, tableClass) {

    /**
     * Declare a Column, supported types follow : [MySQL Data types](https://dev.mysql.com/doc/refman/8.0/en/data-types.html)
     */
    public fun <U : Column<T, *>> column(
            @BuilderInference dsl: MysqlColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
    ): U {
        val columnDsl = MysqlColumnDsl(dsl)
        val column = columnDsl.initialize<U>(columnDsl)
        addColumn(column)
        return column
    }
}

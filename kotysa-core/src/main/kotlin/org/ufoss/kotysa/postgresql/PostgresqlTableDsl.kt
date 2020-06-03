/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.ColumnBuilder
import org.ufoss.kotysa.TableColumnPropertyProvider
import org.ufoss.kotysa.TableDsl
import kotlin.reflect.KClass


public class PostgresqlTableDsl<T : Any>(
        init: PostgresqlTableDsl<T>.(TableColumnPropertyProvider) -> Unit,
        tableClass: KClass<T>
) : TableDsl<T, PostgresqlTableDsl<T>>(init, tableClass) {

    /**
     * Declare a Column, supported types follow : [Postgres Data types](https://www.postgresql.org/docs/11/datatype.html)
     */
    public fun column(dsl: PostgresqlColumnDsl<T>.(TableColumnPropertyProvider) -> ColumnBuilder<*, T, *>) {
        val columnDsl = PostgresqlColumnDsl(dsl)
        val column = columnDsl.initialize(columnDsl, this)
        addColumn(column)
    }
}

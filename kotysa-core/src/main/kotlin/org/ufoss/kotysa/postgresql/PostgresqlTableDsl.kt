/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.Column
import org.ufoss.kotysa.DbType
import org.ufoss.kotysa.TableColumnPropertyProvider
import org.ufoss.kotysa.TableDsl
import kotlin.reflect.KClass


public class PostgresqlTableDsl<T : Any>(
        init: PostgresqlTableDsl<T>.() -> Unit,
        tableClass: KClass<T>,
        private val dbType: DbType
) : TableDsl<T, PostgresqlTableDsl<T>>(init, tableClass) {

    /**
     * Declare a Column, supported types follow : [Postgres Data types](https://www.postgresql.org/docs/11/datatype.html)
     */
    public fun <U : Column<T, *>> column(@BuilderInference dsl: PostgresqlColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U): U {
        val columnDsl = PostgresqlColumnDsl(dsl, dbType)
        val column = columnDsl.initialize<U>(columnDsl)
        addColumn(column)
        return column
    }
}

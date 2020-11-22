/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.h2

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.StringDbVarcharColumnNotNull

/**
 * Represents a H2 Table
 *
 * **Extend this class with an object**
 * @param T Entity type associated with this table
 */
public abstract class H2Table<T : Any> protected constructor(tableName: String? = null) : Table<T>(tableName) {

    /**
     * Declare a Column, supported types follow : [H2 Data types](http://h2database.com/html/datatypes.html)
     */
    protected fun <U : DbColumn<T, *>> column(
            @BuilderInference dsl: H2ColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
    ): U {
        val columnDsl = H2ColumnDsl(dsl)
        val column = columnDsl.initialize<U>(columnDsl)
        return column.also { addColumn(it) }
    }

    /*protected fun <V : Any> foreignKey(
            referencedTable: H2Table<V>,
            vararg columns: DbColumn<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(referencedTable, columns.toList(), fkName))
    }*/

    protected fun <U : DbColumn<T, *>, V : Any> U.foreignKey(references: DbColumn<V, *>, fkName: String? = null): U = this.also {
        foreignKeys.add(ForeignKey(mapOf(this to references), fkName))
    }

    protected fun varchar(getter: (T) -> String, columnName: String? = null, size: Int? = null): StringDbVarcharColumnNotNull<T> =
        StringDbVarcharColumnNotNull(getter, columnName, size).also { addColumn(it) }
}

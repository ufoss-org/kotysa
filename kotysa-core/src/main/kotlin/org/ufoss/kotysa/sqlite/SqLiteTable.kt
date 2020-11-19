/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.sqlite

import org.ufoss.kotysa.*
import org.ufoss.kotysa.columns.StringDbTextColumnNotNull

/**
 * Represents a SqLite Table
 *
 * **Extend this class with an object**
 * @param T Entity type associated with this table
 */
public abstract class SqLiteTable<T : Any> protected constructor(tableName: String? = null) : Table<T>(tableName) {

    /**
     * Declare a Column, supported types follow : [SqLite Data types](https://www.sqlite.org/datatype3.html)
     */
    protected fun <U : DbColumn<T, *>> column(
            @BuilderInference dsl: SqLiteColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
    ): U {
        val columnDsl = SqLiteColumnDsl(dsl)
        val column = columnDsl.initialize<U>(columnDsl)
        addColumn(column)
        return column
    }

    protected fun text(getter: (T) -> String, name: String? = null): StringDbTextColumnNotNull<T> {
        return StringDbTextColumnNotNull(getter, name)
    }

    protected fun <V : Any> foreignKey(
            referencedTable: SqLiteTable<V>,
            vararg columns: DbColumn<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(referencedTable, columns.toList(), fkName))
    }

    protected fun <U : Column<T, *>, V : Any> U.foreignKey(referencedTable: SqLiteTable<V>, fkName: String? = null): U {
        foreignKeys.add(ForeignKey(referencedTable, listOf(this), fkName))
        return this
    }
}

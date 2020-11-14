package org.ufoss.kotysa.sqlite

import org.ufoss.kotysa.DbColumn
import org.ufoss.kotysa.Table
import org.ufoss.kotysa.TableColumnPropertyProvider

/**
 * Represents a SqLite Table
 *
 * **Extend this class with an object**
 * @param T Entity type associated with this table
 */
public abstract class SqLiteTable<T : Any> : Table<T>() {

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
}

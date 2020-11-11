package org.ufoss.kotysa.h2

import org.ufoss.kotysa.columns.Column
import org.ufoss.kotysa.Table
import org.ufoss.kotysa.TableColumnPropertyProvider

/**
 * Represents a H2 Table
 *
 * **Mandatory** extend this class with an object
 * @param T Entity type associated with this table
 */
public abstract class H2Table<T : Any> : Table<T> {

    override var name: String? = null

    /**
     * Declare a Column, supported types follow : [H2 Data types](http://h2database.com/html/datatypes.html)
     */
    protected fun <U : Column<T, *>> column(
            @BuilderInference dsl: H2ColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
    ): U {
        val columnDsl = H2ColumnDsl(dsl)
        return columnDsl.initialize<U>(columnDsl)
    }
}

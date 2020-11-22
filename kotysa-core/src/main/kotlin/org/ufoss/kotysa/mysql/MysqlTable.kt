package org.ufoss.kotysa.mysql

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

import org.ufoss.kotysa.*

/**
 * Represents a MySQL Table
 *
 * **Extend this class with an object**
 * @param T Entity type associated with this table
 */
public abstract class MysqlTable<T : Any> protected constructor(tableName: String? = null) : Table<T>(tableName) {

    /**
     * Declare a Column, supported types follow : [MySQL Data types](https://dev.mysql.com/doc/refman/8.0/en/data-types.html)
     */
    protected fun <U : DbColumn<T, *>> column(
            @BuilderInference dsl: MysqlColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
    ): U {
        val columnDsl = MysqlColumnDsl(dsl)
        val column = columnDsl.initialize<U>(columnDsl)
        addColumn(column)
        return column
    }

    /*protected fun <V : Any> foreignKey(
            referencedTable: MysqlTable<V>,
            vararg columns: DbColumn<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(referencedTable, columns.toList(), fkName))
    }*/

    protected fun <U : DbColumn<T, *>, V : Any> U.foreignKey(references: DbColumn<V, *>, fkName: String? = null): U {
        foreignKeys.add(ForeignKey(mapOf(this to references), fkName))
        return this
    }
}

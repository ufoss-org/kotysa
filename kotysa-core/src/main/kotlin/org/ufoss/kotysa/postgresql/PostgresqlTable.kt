/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.*

/**
 * Represents a PostgreSQL Table
 *
 * **Extend this class with an object**
 * @param T Entity type associated with this table
 */
public abstract class PostgresqlTable<T : Any> protected constructor(tableName: String? = null) : Table<T>(tableName) {

    /**
     * Declare a Column, supported types follow : [Postgres Data types](https://www.postgresql.org/docs/11/datatype.html)
     */
    protected fun <U : DbColumn<T, *>> column(
            @BuilderInference dsl:PostgresqlColumnDsl<T, U>.(TableColumnPropertyProvider<T>) -> U
    ): U {
        val columnDsl = PostgresqlColumnDsl(dsl)
        val column = columnDsl.initialize<U>(columnDsl)
        addColumn(column)
        return column
    }

    protected fun <V : Any> foreignKey(
            referencedTable: PostgresqlTable<V>,
            vararg columns: DbColumn<T, *>,
            fkName: String? = null
    ) {
        foreignKeys.add(ForeignKey(referencedTable, columns.toList(), fkName))
    }

    protected fun <U : Column<T, *>, V : Any> U.foreignKey(referencedTable: PostgresqlTable<V>, fkName: String? = null): U {
        foreignKeys.add(ForeignKey(referencedTable, listOf(this), fkName))
        return this
    }
}

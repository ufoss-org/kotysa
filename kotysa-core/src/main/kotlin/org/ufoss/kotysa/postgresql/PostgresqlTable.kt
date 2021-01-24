/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.postgresql

import org.ufoss.kotysa.*

/**
 * Represents a PostgreSQL Table
 *
 * **Extend this class with an object**
 *
 * supported types follow : [Postgres Data types](https://www.postgresql.org/docs/11/datatype.html)
 * @param T Entity type associated with this table
 */
public abstract class PostgresqlTable<T : Any> protected constructor(tableName: String? = null) : Table<T>(tableName) {

    /*protected fun <V : Any> foreignKey(
            referencedTable: PostgresqlTable<V>,
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

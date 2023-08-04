/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteDatabase
import org.ufoss.kotysa.*

internal class SqlClientDeleteSqLite private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstDelete<T : Any> internal constructor(
        override val client: SQLiteDatabase,
        override val tables: Tables,
        override val table: Table<T>,
    ) : FirstDeleteOrUpdate<T, SqlClientDeleteOrUpdate.Where<T>>(DbAccessType.ANDROID, Module.SQLITE),
        SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>, Return<T> {
        
        override val where = Where(client, properties)
        override val fromTable : FromTable<*> by lazy {
            Delete(client, properties)
        }

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<T, U, SqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    @Suppress("UNCHECKED_CAST")
    internal class Delete<T : Any>(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, SqlClientDeleteOrUpdate.Where<Any>>(),
            SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        override val where = Where(client, properties as Properties<Any>)
        override val fromTable = this

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<T, U, SqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Where<T : Any>(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, SqlClientDeleteOrUpdate.Where<T>>(), SqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>, SqlClientDeleteOrUpdate.Return {
        val client: SQLiteDatabase

        override fun execute() = with(properties) {
            val statement = client.compileStatement(deleteFromTableSql())

            // add all values from where part
            bindParameters(statement)

            statement.executeUpdateDelete()
        }
    }
}

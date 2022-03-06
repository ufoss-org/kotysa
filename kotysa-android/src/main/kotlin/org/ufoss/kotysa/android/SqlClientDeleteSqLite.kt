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
    ) : FirstDeleteOrUpdate<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>,
            SqlClientDeleteOrUpdate.Where<T>>(DbAccessType.ANDROID, Module.SQLITE),
        SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>, Return<T> {
        
        override val where = Where(client, properties)
        override val from: SqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Delete(client, properties)
        }
    }

    internal class Delete<T : Any>(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, SqlClientDeleteOrUpdate.Where<Any>>(),
            SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(client, properties as Properties<Any>)
        override val from = this
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
            bindWhereArgs(statement)

            statement.executeUpdateDelete()
        }
    }
}

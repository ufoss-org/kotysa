/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteDatabase
import org.ufoss.kotysa.*

internal class SqlClientUpdateSqLite private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstUpdate<T : Any> internal constructor(
            override val client: SQLiteDatabase,
            override val tables: Tables,
            override val table: Table<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Update<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, T,
            SqlClientDeleteOrUpdate.Where<T>, SqlClientDeleteOrUpdate.Update<T>>(DbAccessType.ANDROID, Module.SQLITE),
            SqlClientDeleteOrUpdate.Update<T>, Return<T> {
        override val where = Where(client, properties) // fixme try with a lazy
        override val update = this
        override val from: SqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Update(client, properties)
        }
    }

    internal class Update<T : Any> internal constructor(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Any,
            SqlClientDeleteOrUpdate.Where<Any>>(),
            SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(client, properties as Properties<Any>) // fixme try with a lazy
        override val from = this
    }

    internal class Where<T : Any>(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, SqlClientDeleteOrUpdate.Where<T>>(), SqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>, SqlClientDeleteOrUpdate.Return {
        val client: SQLiteDatabase

        override fun execute() = with(properties) {
            val statement = client.compileStatement(updateTableSql())

            var index = 1
            // 1) add all values from set part
            setValues.values.forEach { value -> statement.bind(index++, value) }
            // 2) add all values from where part
            bindWhereArgs(statement, index)

            statement.executeUpdateDelete()
        }
    }
}

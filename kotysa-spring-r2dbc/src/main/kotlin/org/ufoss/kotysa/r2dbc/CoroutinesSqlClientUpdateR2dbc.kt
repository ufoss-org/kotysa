/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.ufoss.kotysa.*


internal class CoroutinesSqlClientUpdateR2dbc private constructor() : AbstractSqlClientUpdateR2dbc() {

    internal class FirstUpdate<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val tables: Tables,
            override val table: Table<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Update<T, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, T,
            CoroutinesSqlClientDeleteOrUpdate.Where<T>, CoroutinesSqlClientDeleteOrUpdate.Update<T>>(DbAccessType.R2DBC),
            CoroutinesSqlClientDeleteOrUpdate.Update<T>, Return<T> {
        override val where = Where(client, properties) // fixme try with a lazy
        override val update = this
        override val from: CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Update(client, properties)
        }
    }

    internal class Update<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientDeleteOrUpdate.DeleteOrUpdate<T, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Any,
            CoroutinesSqlClientDeleteOrUpdate.Where<Any>>(),
            CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(client, properties as Properties<Any>) // fixme try with a lazy
        override val from = this
    }

    internal class Where<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, CoroutinesSqlClientDeleteOrUpdate.Where<T>>(), CoroutinesSqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : AbstractSqlClientUpdateR2dbc.Return<T>, CoroutinesSqlClientDeleteOrUpdate.Return {

        override suspend fun execute(): Int = fetch().awaitRowsUpdated()
    }
}

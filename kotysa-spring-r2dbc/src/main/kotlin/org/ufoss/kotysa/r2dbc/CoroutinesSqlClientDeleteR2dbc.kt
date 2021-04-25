/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.ufoss.kotysa.*


internal class CoroutinesSqlClientDeleteR2dbc private constructor() : AbstractSqlClientDeleteR2dbc() {

    internal class FirstDelete<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val tables: Tables,
            override val table: Table<T>,
    ) : FirstDeleteOrUpdate<T, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, T,
            CoroutinesSqlClientDeleteOrUpdate.Where<T>>(DbAccessType.R2DBC),
            CoroutinesSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>, Return<T> {
        override val where = Where(client, properties)
        override val from: CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Delete(client, properties)
        }
    }

    internal class Delete<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Any, CoroutinesSqlClientDeleteOrUpdate.Where<Any>>(),
            CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(client, properties as Properties<Any>)
        override val from = this
    }

    internal class Where<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, CoroutinesSqlClientDeleteOrUpdate.Where<T>>(), CoroutinesSqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : AbstractSqlClientDeleteR2dbc.Return<T>, CoroutinesSqlClientDeleteOrUpdate.Return {

        override suspend fun execute(): Int = fetch().awaitRowsUpdated()
    }
}

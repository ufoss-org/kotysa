/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import reactor.core.publisher.Mono


internal class SqlClientDeleteR2dbc private constructor() : AbstractSqlClientDeleteR2dbc() {

    internal class FirstDelete<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val tables: Tables,
            override val table: Table<T>,
    ) : FirstDeleteOrUpdate<T, ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, T, ReactorSqlClientDeleteOrUpdate.Where<T>>(),
            ReactorSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>, Return<T> {
        override val where = Where(client, properties)
        override val from: ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Delete(client, properties)
        }
    }

    internal class Delete<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Any, ReactorSqlClientDeleteOrUpdate.Where<Any>>(),
            ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(client, properties as Properties<Any>)
        override val from = this
    }

    internal class Where<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, ReactorSqlClientDeleteOrUpdate.Where<T>>(), ReactorSqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : AbstractSqlClientDeleteR2dbc.Return<T>, ReactorSqlClientDeleteOrUpdate.Return {

        override fun execute(): Mono<Int> = fetch().rowsUpdated()
    }
}

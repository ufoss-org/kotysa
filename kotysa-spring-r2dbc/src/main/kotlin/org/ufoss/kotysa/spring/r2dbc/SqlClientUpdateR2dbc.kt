/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import reactor.core.publisher.Mono


internal class SqlClientUpdateR2dbc private constructor() : AbstractSqlClientUpdateR2dbc() {

    internal class FirstUpdate<T : Any> internal constructor(
        override val client: DatabaseClient,
        override val tables: Tables,
        override val table: Table<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Update<T, ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, T,
            ReactorSqlClientDeleteOrUpdate.Where<T>,
            ReactorSqlClientDeleteOrUpdate.Update<T>>(DbAccessType.R2DBC, Module.SPRING_R2DBC),
        ReactorSqlClientDeleteOrUpdate.Update<T>, Return<T> {
        override val where = Where(client, properties) // fixme try with a lazy
        override val update = this
        override val from: ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Update(client, properties)
        }
    }

    internal class Update<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientDeleteOrUpdate.DeleteOrUpdate<T, ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Any,
            ReactorSqlClientDeleteOrUpdate.Where<Any>>(),
        ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(client, properties as Properties<Any>) // fixme try with a lazy
        override val from = this
    }

    internal class Where<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, ReactorSqlClientDeleteOrUpdate.Where<T>>(),
        ReactorSqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : AbstractSqlClientUpdateR2dbc.Return<T>, ReactorSqlClientDeleteOrUpdate.Return {

        override fun execute(): Mono<Int> = fetch().rowsUpdated()
    }
}

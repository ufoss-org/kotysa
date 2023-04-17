/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import reactor.core.publisher.Mono


internal class SqlClientDeleteSpringR2dbc private constructor() : AbstractSqlClientDeleteSpringR2dbc() {

    internal class FirstDelete<T : Any> internal constructor(
        override val client: DatabaseClient,
        override val tables: Tables,
        override val table: Table<T>,
    ) : FirstDeleteOrUpdate<T, ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T>,
            ReactorSqlClientDeleteOrUpdate.Where<T>>(DbAccessType.R2DBC, Module.SPRING_R2DBC),
        ReactorSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>, Return<T> {
        
        override val where = Where(client, properties)
        override val fromTable: ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Delete(client, properties)
        }
    }

    internal class Delete<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, ReactorSqlClientDeleteOrUpdate.Where<Any>>(),
        ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(client, properties as Properties<Any>)
        override val fromTable = this
    }

    internal class Where<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, ReactorSqlClientDeleteOrUpdate.Where<T>>(),
        ReactorSqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : AbstractSqlClientDeleteSpringR2dbc.Return<T>, ReactorSqlClientDeleteOrUpdate.Return {

        override fun execute(): Mono<Long> = fetch().rowsUpdated()
    }
}

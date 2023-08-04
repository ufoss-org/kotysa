/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.*
import reactor.core.publisher.Mono


internal class ReactorSqlClientUpdateSpringR2dbc private constructor() : AbstractSqlClientUpdateSpringR2dbc() {

    internal class FirstUpdate<T : Any> internal constructor(
        override val client: DatabaseClient,
        override val tables: Tables,
        override val table: Table<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Update<T, ReactorSqlClientDeleteOrUpdate.Where<T>,
            ReactorSqlClientDeleteOrUpdate.Update<T>, ReactorSqlClientDeleteOrUpdate.UpdateInt<T>>
        (DbAccessType.R2DBC, Module.SPRING_R2DBC), ReactorSqlClientDeleteOrUpdate.Update<T>,
        ReactorSqlClientDeleteOrUpdate.UpdateInt<T>, Return<T> {
        override val where = Where(client, properties) // fixme try with a lazy
        override val update = this
        override val updateInt = this
        override val fromTable: ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Update(client, properties)
        }

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<T, U, ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Update<T : Any> internal constructor(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, ReactorSqlClientDeleteOrUpdate.Where<Any>>(),
        ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(client, properties as Properties<Any>) // fixme try with a lazy
        override val fromTable = this

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<T, U, ReactorSqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Where<T : Any>(
            override val client: DatabaseClient,
            override val properties: Properties<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, ReactorSqlClientDeleteOrUpdate.Where<T>>(),
        ReactorSqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : AbstractSqlClientUpdateSpringR2dbc.Return<T>, ReactorSqlClientDeleteOrUpdate.Return {

        override fun execute(): Mono<Long> = fetch().rowsUpdated()
    }
}

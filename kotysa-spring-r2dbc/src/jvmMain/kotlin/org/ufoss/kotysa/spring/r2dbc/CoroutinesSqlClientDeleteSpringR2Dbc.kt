/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.ufoss.kotysa.*


internal class CoroutinesSqlClientDeleteSpringR2Dbc private constructor() : AbstractSqlClientDeleteSpringR2dbc() {

    internal class FirstDelete<T : Any> internal constructor(
        override val client: DatabaseClient,
        override val tables: Tables,
        override val table: Table<T>,
    ) : FirstDeleteOrUpdate<T, CoroutinesSqlClientDeleteOrUpdate.Where<T>>(DbAccessType.R2DBC, Module.SPRING_R2DBC),
        CoroutinesSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>, Return<T> {

        override val where = Where(client, properties)
        override val fromTable: FromTable<*> by lazy {
            Delete(client, properties)
        }

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<T, U, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Delete<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, CoroutinesSqlClientDeleteOrUpdate.Where<Any>>(),
        CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(client, properties as Properties<Any>)
        override val fromTable = this

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<T, U, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Where<T : Any>(
        override val client: DatabaseClient,
        override val properties: Properties<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, CoroutinesSqlClientDeleteOrUpdate.Where<T>>(),
        CoroutinesSqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : AbstractSqlClientDeleteSpringR2dbc.Return<T>,
        CoroutinesSqlClientDeleteOrUpdate.Return {

        override suspend fun execute(): Long = fetch().awaitRowsUpdated()
    }
}

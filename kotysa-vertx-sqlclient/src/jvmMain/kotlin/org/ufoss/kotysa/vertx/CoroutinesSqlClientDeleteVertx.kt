/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import io.vertx.kotlin.coroutines.await
import io.vertx.sqlclient.Pool
import io.vertx.sqlclient.Tuple
import org.ufoss.kotysa.*

internal class CoroutinesSqlClientDeleteVertx private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstDelete<T : Any> internal constructor(
        override val pool: Pool,
        override val tables: Tables,
        override val table: Table<T>,
    ) : FirstDeleteOrUpdate<T, CoroutinesSqlClientDeleteOrUpdate.Where<T>>(DbAccessType.VERTX, Module.VERTX_SQL_CLIENT),
        CoroutinesSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>, Return<T> {
        override val where = Where(pool, properties)
        override val fromTable: FromTable<*> by lazy {
            Delete(pool, properties)
        }

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<U, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Delete<T : Any>(
        override val pool: Pool,
        override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, CoroutinesSqlClientDeleteOrUpdate.Where<Any>>(),
        CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(pool, properties as Properties<Any>)
        override val fromTable = this

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<U, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Where<T : Any>(
        override val pool: Pool,
        override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, CoroutinesSqlClientDeleteOrUpdate.Where<T>>(),
        CoroutinesSqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>,
        CoroutinesSqlClientDeleteOrUpdate.Return {

        val pool: Pool

        override suspend fun execute(): Long = pool.getVertxConnection().execute { connection ->
            with(properties) {
                connection.preparedQuery(deleteFromTableSql())
                    .execute(Tuple.tuple().apply(::vertxBindParams))
                    .await()
                    .rowCount().toLong()
            }
        }
    }
}

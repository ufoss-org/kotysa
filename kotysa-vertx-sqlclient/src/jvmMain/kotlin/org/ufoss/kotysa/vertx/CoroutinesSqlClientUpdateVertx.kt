/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import io.vertx.kotlin.coroutines.await
import io.vertx.sqlclient.Pool
import io.vertx.sqlclient.Tuple
import org.ufoss.kotysa.*

internal class CoroutinesSqlClientUpdateVertx private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstUpdate<T : Any> internal constructor(
        override val pool: Pool,
        override val tables: Tables,
        override val table: Table<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Update<T, CoroutinesSqlClientDeleteOrUpdate.Where<T>,
            CoroutinesSqlClientDeleteOrUpdate.Update<T>, CoroutinesSqlClientDeleteOrUpdate.UpdateInt<T>>
        (DbAccessType.VERTX, Module.VERTX_SQL_CLIENT), CoroutinesSqlClientDeleteOrUpdate.Update<T>,
        CoroutinesSqlClientDeleteOrUpdate.UpdateInt<T>, Return<T> {
        override val where = Where(pool, properties) // fixme try with a lazy
        override val update = this
        override val updateInt = this
        override val fromTable: CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Update(pool, properties)
        }

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<U, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Update<T : Any> internal constructor(
        override val pool: Pool,
        override val properties: Properties<T>
    ) : DeleteOrUpdate<T, CoroutinesSqlClientDeleteOrUpdate.Where<Any>>(),
        CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(pool, properties as Properties<Any>) // fixme try with a lazy
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
                require(updateClauses.isNotEmpty()) { "At least one value must be set in Update" }
                
                connection.preparedQuery(updateTableSql())
                    .execute(Tuple.tuple().apply(::vertxBindParams))
                    .await()
                    .rowCount().toLong()
            }
        }
    }
}

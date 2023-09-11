/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import io.vertx.mutiny.sqlclient.Pool
import io.vertx.mutiny.sqlclient.Tuple
import org.ufoss.kotysa.*

internal class MutinySqlClientDeleteVertx private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstDelete<T : Any> internal constructor(
        override val pool: Pool,
        override val tables: Tables,
        override val table: Table<T>,
    ) : FirstDeleteOrUpdate<T, MutinySqlClientDeleteOrUpdate.Where<T>>(DbAccessType.VERTX, Module.VERTX_SQL_CLIENT),
        MutinySqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>,
        Return<T> {
        
        override val where = Where(pool, properties)
        override val fromTable: MutinySqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Delete(pool, properties)
        }

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<T, U, MutinySqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Delete<T : Any>(
            override val pool: Pool,
            override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, MutinySqlClientDeleteOrUpdate.Where<Any>>(), MutinySqlClientDeleteOrUpdate.DeleteOrUpdate<T>,
        Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(pool, properties as Properties<Any>)
        override val fromTable = this

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<T, U, MutinySqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Where<T : Any>(
            override val pool: Pool,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, MutinySqlClientDeleteOrUpdate.Where<T>>(),
        MutinySqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>, MutinySqlClientDeleteOrUpdate.Return {
        val pool: Pool

        override fun execute() = with(properties) {
            pool.getVertxConnection().executeUni { connection ->
                connection.preparedQuery(deleteFromTableSql())
                    .execute(Tuple.tuple().apply(::vertxBindParams))
                    .map { rowSet -> rowSet.rowCount() }
            }
        }
    }
}

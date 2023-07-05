/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import io.vertx.mutiny.sqlclient.Pool
import io.vertx.mutiny.sqlclient.Tuple
import org.ufoss.kotysa.*

internal class SqlClientUpdateVertx private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstUpdate<T : Any> internal constructor(
            override val pool: Pool,
            override val tables: Tables,
            override val table: Table<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Update<T,  MutinySqlClientDeleteOrUpdate.Where<T>,
            MutinySqlClientDeleteOrUpdate.Update<T>,
            MutinySqlClientDeleteOrUpdate.UpdateInt<T>>(DbAccessType.VERTX, Module.VERTX_SQL_CLIENT),
        MutinySqlClientDeleteOrUpdate.Update<T>, MutinySqlClientDeleteOrUpdate.UpdateInt<T>, Return<T> {
        override val where = Where(pool, properties) // fixme try with a lazy
        override val update = this
        override val updateInt = this
        override val fromTable: MutinySqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Update(pool, properties)
        }

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<T, U, MutinySqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Update<T : Any> internal constructor(
            override val pool: Pool,
            override val properties: Properties<T>
    ) : DeleteOrUpdate<T, MutinySqlClientDeleteOrUpdate.Where<Any>>(), MutinySqlClientDeleteOrUpdate.DeleteOrUpdate<T>,
        Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(pool, properties as Properties<Any>) // fixme try with a lazy
        override val fromTable = this

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<T, U, MutinySqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Where<T : Any>(
            override val pool: Pool,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, MutinySqlClientDeleteOrUpdate.Where<T>>(), MutinySqlClientDeleteOrUpdate.Where<T>,
        Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>, MutinySqlClientDeleteOrUpdate.Return {
        val pool: Pool

        override fun execute() = with(properties) {
            require(updateClauses.isNotEmpty()) { "At least one value must be set in Update" }

            pool.getVertxConnection().executeUni { connection ->
                connection.preparedQuery(updateTableSql())
                    .execute(Tuple.tuple().apply(::vertxBindParams))
                    .map { rowSet -> rowSet.rowCount() }
            }
        }
    }
}

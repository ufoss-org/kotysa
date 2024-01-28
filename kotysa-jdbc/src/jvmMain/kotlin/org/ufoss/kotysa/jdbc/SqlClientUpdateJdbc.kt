/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.*
import org.ufoss.kotysa.core.jdbc.jdbcBindParams

internal class SqlClientUpdateJdbc private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstUpdate<T : Any> internal constructor(
        override val jdbcConnection: JdbcConnection,
        override val tables: Tables,
        override val table: Table<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Update<T, SqlClientDeleteOrUpdate.Where<T>, SqlClientDeleteOrUpdate.Update<T>,
            SqlClientDeleteOrUpdate.UpdateInt<T>>(DbAccessType.JDBC, Module.JDBC), SqlClientDeleteOrUpdate.Update<T>,
        SqlClientDeleteOrUpdate.UpdateInt<T>,
        Return<T> {
        override val where = Where(jdbcConnection, properties) // fixme try with a lazy
        override val update = this
        override val updateInt = this
        override val fromTable: Update<T> by lazy {
            Update(jdbcConnection, properties)
        }

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<U, SqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Update<T : Any> internal constructor(
        override val jdbcConnection: JdbcConnection,
        override val properties: Properties<T>
    ) : DeleteOrUpdate<T, SqlClientDeleteOrUpdate.Where<Any>>(), SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(jdbcConnection, properties as Properties<Any>) // fixme try with a lazy
        override val fromTable = this

        override fun <U : Any> innerJoin(
            table: Table<U>
        ): SqlClientQuery.Joinable<U, SqlClientDeleteOrUpdate.DeleteOrUpdate<U>> =
            joinProtected(table, JoinClauseType.INNER)
    }

    internal class Where<T : Any>(
        override val jdbcConnection: JdbcConnection,
        override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, SqlClientDeleteOrUpdate.Where<T>>(), SqlClientDeleteOrUpdate.Where<T>,
        Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>, SqlClientDeleteOrUpdate.Return {
        val jdbcConnection: JdbcConnection

        override fun execute() = jdbcConnection.execute { connection ->
            with(properties) {
                require(updateClauses.isNotEmpty()) { "At least one value must be set in Update" }

                val statement = connection.prepareStatement(updateTableSql())

                // 1) add all values from update and where part
                jdbcBindParams(statement)
                // 2) reset index
                index = 0

                statement.executeUpdate()
            }
        }
    }
}

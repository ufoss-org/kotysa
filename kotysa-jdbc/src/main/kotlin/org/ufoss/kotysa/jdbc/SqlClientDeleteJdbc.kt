/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.*
import org.ufoss.kotysa.core.jdbc.jdbcBindParams

internal class SqlClientDeleteJdbc private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstDelete<T : Any> internal constructor(
        override val jdbcConnection: JdbcConnection,
        override val tables: Tables,
        override val table: Table<T>,
    ) : FirstDeleteOrUpdate<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, T,
            SqlClientDeleteOrUpdate.Where<T>>(DbAccessType.JDBC, Module.JDBC),
        SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>, Return<T> {
        override val where = Where(jdbcConnection, properties)
        override val from: SqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Delete(jdbcConnection, properties)
        }
    }

    internal class Delete<T : Any>(
            override val jdbcConnection: JdbcConnection,
            override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Any, SqlClientDeleteOrUpdate.Where<Any>>(),
            SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(jdbcConnection, properties as Properties<Any>)
        override val from = this
    }

    internal class Where<T : Any>(
            override val jdbcConnection: JdbcConnection,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, SqlClientDeleteOrUpdate.Where<T>>(), SqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>, SqlClientDeleteOrUpdate.Return {
        val jdbcConnection: JdbcConnection

        override fun execute() = jdbcConnection.execute { connection ->
            with(properties) {
                val statement = connection.prepareStatement(deleteFromTableSql())
                // 1) add all values from where part
                jdbcBindParams(statement)
                // 2) reset index
                index = 0

                statement.executeUpdate()
            }
        }
    }
}

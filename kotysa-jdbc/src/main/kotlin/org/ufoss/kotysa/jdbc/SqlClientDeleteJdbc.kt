/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.*
import java.sql.Connection

internal class SqlClientDeleteJdbc private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstDelete<T : Any> internal constructor(
        override val connection: Connection,
        override val tables: Tables,
        override val table: Table<T>,
    ) : FirstDeleteOrUpdate<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, T,
            SqlClientDeleteOrUpdate.Where<T>>(DbAccessType.JDBC, Module.JDBC),
        SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>, Return<T> {
        override val where = Where(connection, properties)
        override val from: SqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Delete(connection, properties)
        }
    }

    internal class Delete<T : Any>(
            override val connection: Connection,
            override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Any, SqlClientDeleteOrUpdate.Where<Any>>(),
            SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(connection, properties as Properties<Any>)
        override val from = this
    }

    internal class Where<T : Any>(
            override val connection: Connection,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, SqlClientDeleteOrUpdate.Where<T>>(), SqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>, SqlClientDeleteOrUpdate.Return {
        val connection: Connection

        override fun execute() = with(properties) {
            val statement = connection.prepareStatement(deleteFromTableSql())
            // 1) add all values from where part
            jdbcBindWhereParams(statement)
            // 2) reset index
            index = 0

            statement.executeUpdate()
        }
    }
}

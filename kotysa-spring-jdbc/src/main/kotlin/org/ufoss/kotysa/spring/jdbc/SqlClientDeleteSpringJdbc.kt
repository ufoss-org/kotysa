/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.ufoss.kotysa.*

internal class SqlClientDeleteSpringJdbc private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstDelete<T : Any> internal constructor(
            override val client: NamedParameterJdbcOperations,
            override val tables: Tables,
            override val table: Table<T>,
    ) : FirstDeleteOrUpdate<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, T, SqlClientDeleteOrUpdate.Where<T>>(),
            SqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>, Return<T> {
        override val where = Where(client, properties)
        override val from: SqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Delete(client, properties)
        }
    }

    internal class Delete<T : Any>(
            override val client: NamedParameterJdbcOperations,
            override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Any, SqlClientDeleteOrUpdate.Where<Any>>(),
            SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(client, properties as Properties<Any>)
        override val from = this
    }

    internal class Where<T : Any>(
            override val client: NamedParameterJdbcOperations,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, SqlClientDeleteOrUpdate.Where<T>>(), SqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>, SqlClientDeleteOrUpdate.Return {
        val client: NamedParameterJdbcOperations

        override fun execute() = with(properties) {
            val parameters = MapSqlParameterSource()
            bindWhereParams(parameters)

            client.update(deleteFromTableSql(), parameters)
        }
    }
}

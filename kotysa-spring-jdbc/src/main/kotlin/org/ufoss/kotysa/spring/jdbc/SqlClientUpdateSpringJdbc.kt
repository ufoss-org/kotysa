/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.ufoss.kotysa.*


internal class SqlClientUpdateSpringJdbc private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstUpdate<T : Any> internal constructor(
            override val client: NamedParameterJdbcOperations,
            override val tables: Tables,
            override val table: Table<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Update<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>,
            SqlClientDeleteOrUpdate.Where<T>, SqlClientDeleteOrUpdate.Update<T>>(DbAccessType.JDBC, Module.SPRING_JDBC),
            SqlClientDeleteOrUpdate.Update<T>, Return<T> {
        override val where = Where(client, properties) // fixme try with a lazy
        override val update = this
        override val fromTable: SqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Update(client, properties)
        }
    }

    internal class Update<T : Any> internal constructor(
            override val client: NamedParameterJdbcOperations,
            override val properties: Properties<T>
    ) : DeleteOrUpdate<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>,
            SqlClientDeleteOrUpdate.Where<Any>>(),
            SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(client, properties as Properties<Any>) // fixme try with a lazy
        override val fromTable = this
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
            require(setValues.isNotEmpty()) { "At least one value must be set in Update" }

            val parameters = MapSqlParameterSource()

            // 1) add all values from set part
            setValues.values
                    .map { value -> tables.getDbValue(value) }
                    .forEach { value -> parameters.addValue("k${index++}", value) }
            // 2) add all values from where part
            springJdbcBindParams(parameters)
            // 3) reset index
            index = 0

            client.update(updateTableSql(), parameters)
        }
    }
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.*
import org.ufoss.kotysa.core.jdbc.jdbcBindWhereParams

internal class SqlClientUpdateJdbc private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstUpdate<T : Any> internal constructor(
        override val jdbcConnection: JdbcConnection,
        override val tables: Tables,
        override val table: Table<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Update<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, T,
            SqlClientDeleteOrUpdate.Where<T>, SqlClientDeleteOrUpdate.Update<T>>(DbAccessType.JDBC, Module.JDBC),
            SqlClientDeleteOrUpdate.Update<T>, Return<T> {
        override val where = Where(jdbcConnection, properties) // fixme try with a lazy
        override val update = this
        override val from: SqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Update(jdbcConnection, properties)
        }
    }

    internal class Update<T : Any> internal constructor(
        override val jdbcConnection: JdbcConnection,
        override val properties: Properties<T>
    ) : DeleteOrUpdate<T, SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Any,
            SqlClientDeleteOrUpdate.Where<Any>>(),
            SqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(jdbcConnection, properties as Properties<Any>) // fixme try with a lazy
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
                require(setValues.isNotEmpty()) { "At least one value must be set in Update" }

                val statement = connection.prepareStatement(updateTableSql())

                // 1) add all values from set part
                setValues.values
                    .map { value -> tables.getDbValue(value) }
                    .forEach { dbValue -> statement.setObject(++index, dbValue) }

                // 2) add all values from where part
                jdbcBindWhereParams(statement)
                // 3) reset index
                index = 0

                statement.executeUpdate()
            }
        }
    }
}

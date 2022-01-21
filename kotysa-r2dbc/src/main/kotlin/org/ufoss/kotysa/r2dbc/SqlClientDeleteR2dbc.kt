/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import io.r2dbc.spi.Connection
import kotlinx.coroutines.reactive.awaitSingle
import org.ufoss.kotysa.*

internal class SqlClientDeleteR2dbc private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstDelete<T : Any> internal constructor(
        override val connection: Connection,
        override val tables: Tables,
        override val table: Table<T>,
    ) : FirstDeleteOrUpdate<T, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, T,
            CoroutinesSqlClientDeleteOrUpdate.Where<T>>(DbAccessType.R2DBC, Module.R2DBC),
        CoroutinesSqlClientDeleteOrUpdate.FirstDeleteOrUpdate<T>, Return<T> {
        override val where = Where(connection, properties)
        override val from: CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Delete(connection, properties)
        }
    }

    internal class Delete<T : Any>(
            override val connection: Connection,
            override val properties: Properties<T>,
    ) : DeleteOrUpdate<T, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Any, CoroutinesSqlClientDeleteOrUpdate.Where<Any>>(),
            CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(connection, properties as Properties<Any>)
        override val from = this
    }

    internal class Where<T : Any>(
            override val connection: Connection,
            override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, CoroutinesSqlClientDeleteOrUpdate.Where<T>>(), CoroutinesSqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>, CoroutinesSqlClientDeleteOrUpdate.Return {
        val connection: Connection

        override suspend fun execute(): Int = with(properties) {
            val statement = connection.createStatement(deleteFromTableSql())
            // reset index
            index = 0

            // 1) add all values from where part
            whereClauses
                .dbValues(tables)
                .forEach { value ->
                    statement.bind(index++, value)
                }

            // reset index
            index = 0

            val result = statement.execute().awaitSingle()
            result.rowsUpdated.awaitSingle()
        }
    }
}
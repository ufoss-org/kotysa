/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.reactive.awaitSingle
import org.ufoss.kotysa.*
import org.ufoss.kotysa.core.r2dbc.r2dbcBindWhereParams
import kotlin.reflect.KClass

internal class SqlClientUpdateR2dbc private constructor() : DefaultSqlClientDeleteOrUpdate() {

    internal class FirstUpdate<T : Any> internal constructor(
        override val connectionFactory: ConnectionFactory,
        override val tables: Tables,
        override val table: Table<T>,
    ) : DefaultSqlClientDeleteOrUpdate.Update<T, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, T,
            CoroutinesSqlClientDeleteOrUpdate.Where<T>,
            CoroutinesSqlClientDeleteOrUpdate.Update<T>>(DbAccessType.R2DBC, Module.R2DBC),
        CoroutinesSqlClientDeleteOrUpdate.Update<T>, Return<T> {
        override val where = Where(connectionFactory, properties) // fixme try with a lazy
        override val update = this
        override val from: CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T> by lazy {
            Update(connectionFactory, properties)
        }
    }

    internal class Update<T : Any> internal constructor(
        override val connectionFactory: ConnectionFactory,
        override val properties: Properties<T>
    ) : DeleteOrUpdate<T, CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Any,
            CoroutinesSqlClientDeleteOrUpdate.Where<Any>>(),
        CoroutinesSqlClientDeleteOrUpdate.DeleteOrUpdate<T>, Return<T> {
        @Suppress("UNCHECKED_CAST")
        override val where = Where(connectionFactory, properties as Properties<Any>) // fixme try with a lazy
        override val from = this
    }

    internal class Where<T : Any>(
        override val connectionFactory: ConnectionFactory,
        override val properties: Properties<T>
    ) : DefaultSqlClientDeleteOrUpdate.Where<T, CoroutinesSqlClientDeleteOrUpdate.Where<T>>(),
        CoroutinesSqlClientDeleteOrUpdate.Where<T>, Return<T> {
        override val where = this
    }

    private interface Return<T : Any> : DefaultSqlClientDeleteOrUpdate.Return<T>,
        CoroutinesSqlClientDeleteOrUpdate.Return {
        val connectionFactory: ConnectionFactory

        override suspend fun execute(): Int = getR2dbcConnection(connectionFactory).execute { connection ->
            with(properties) {
                require(setValues.isNotEmpty()) { "At least one value must be set in Update" }

                val statement = connection.createStatement(updateTableSql())
                // reset index
                index = 0

                // 1) add all values from set part
                setValues.entries
                    .forEach { entry ->
                        val value = entry.value
                        if (value == null) {
                            statement.bindNull(
                                index++,
                                ((entry.key as DbColumn<*, *>).entityGetter.toCallable().returnType.classifier as KClass<*>).toDbClass().java
                            )
                        } else {
                            statement.bind(index++, tables.getDbValue(value)!!)
                        }
                    }

                // 2) add all values from where part
                r2dbcBindWhereParams(statement)

                // reset index
                index = 0

                val result = statement.execute().awaitSingle()
                result.rowsUpdated.awaitSingle()
            }
        }
    }
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import io.smallrye.mutiny.Uni
import io.vertx.core.buffer.Buffer
import io.vertx.kotlin.coroutines.await
import io.vertx.mutiny.sqlclient.Pool
import io.vertx.mutiny.sqlclient.Row
import io.vertx.mutiny.sqlclient.Tuple
import org.ufoss.kotysa.DefaultSqlClientCommon
import org.ufoss.kotysa.RowImpl
import org.ufoss.kotysa.SqlType
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.vertx.transaction.CoroutinesVertxTransaction
import org.ufoss.kotysa.vertx.transaction.MutinyVertxTransaction
import kotlin.coroutines.coroutineContext

public fun Row.toRow(): RowImpl = RowImpl(MutinyVertxRow(this))

public fun io.vertx.sqlclient.Row.toRow(): RowImpl = RowImpl(VertxRow(this))

internal fun DefaultSqlClientCommon.Properties.vertxBindParams(tuple: Tuple) {
    parameters
        .flatMap { parameter ->
            if (parameter is Collection<*>) {
                parameter
            } else {
                setOf(parameter)
            }
        }
        .forEach { parameter ->
            tuple.addDbValue(parameter, tables)
        }
}

internal fun <T> Tuple.addDbValue(value: T, tables: Tables, sqlType: SqlType? = null) =
    if (value != null && value is ByteArray) {
        addValue(Buffer.buffer(value))
    } else if (SqlType.BINARY == sqlType) {
        // workaround for MSSQL https://progress-supportcommunity.force.com/s/article/Implicit-conversion-from-data-type-nvarchar-to-varbinary-max-is-not-allowed-error-with-SQL-Server-JDBC-driver
        apply { delegate.addBuffer(null) }
    } else {
        addValue(tables.getDbValue(value))
    }

internal fun DefaultSqlClientCommon.Properties.vertxBindParams(tuple: io.vertx.sqlclient.Tuple) {
    parameters
        .flatMap { parameter ->
            if (parameter is Collection<*>) {
                parameter
            } else {
                setOf(parameter)
            }
        }
        .forEach { parameter ->
            tuple.addDbValue(parameter, tables)
        }
}

internal fun <T> io.vertx.sqlclient.Tuple.addDbValue(value: T, tables: Tables, sqlType: SqlType? = null) =
    if (value != null && value is ByteArray) {
        addValue(Buffer.buffer(value))
    } else if (SqlType.BINARY == sqlType) {
        // workaround for MSSQL https://progress-supportcommunity.force.com/s/article/Implicit-conversion-from-data-type-nvarchar-to-varbinary-max-is-not-allowed-error-with-SQL-Server-JDBC-driver
        apply { addBuffer(null) }
    } else {
        addValue(tables.getDbValue(value))
    }

internal fun Pool.getVertxConnection() =
    // reuse currentTransaction's connection if any, else establish a new connection
    Uni.createFrom().context { context ->
        if (context.contains(MutinyVertxTransaction.ContextKey)
            && !context.get<MutinyVertxTransaction>(MutinyVertxTransaction.ContextKey).isCompleted()
        ) {
            val vertxTransaction: MutinyVertxTransaction = context[MutinyVertxTransaction.ContextKey]
            Uni.createFrom().item(
                MutinyVertxConnection(vertxTransaction.connection, true)
            )
        } else {
            connection
                .map { connection -> MutinyVertxConnection(connection, false) }
        }
    }

internal suspend fun io.vertx.sqlclient.Pool.getVertxConnection(): CoroutinesVertxConnection {
    // reuse currentTransaction's connection if any, else establish a new connection
    val transaction = coroutineContext[CoroutinesVertxTransaction]
    val connection = if (transaction != null && !transaction.isCompleted()) {
        transaction.connection
    } else {
        connection.await()
    }

    return CoroutinesVertxConnection(connection, transaction != null)
}

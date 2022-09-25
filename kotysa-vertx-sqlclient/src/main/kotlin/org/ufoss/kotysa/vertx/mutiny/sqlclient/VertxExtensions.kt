/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import io.smallrye.mutiny.Uni
import io.vertx.core.buffer.Buffer
import io.vertx.mutiny.sqlclient.Pool
import io.vertx.mutiny.sqlclient.Row
import io.vertx.mutiny.sqlclient.Tuple
import org.ufoss.kotysa.DefaultSqlClientCommon
import org.ufoss.kotysa.RowImpl
import org.ufoss.kotysa.SqlType
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.vertx.mutiny.sqlclient.transaction.VertxTransaction

public fun Row.toRow(): RowImpl = RowImpl(VertxRow(this))

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

internal fun Pool.getVertxConnection() =
    // reuse currentTransaction's connection if any, else establish a new connection
    Uni.createFrom().context { context ->
        if (context.contains(VertxTransaction.ContextKey)
            && !context.get<VertxTransaction>(VertxTransaction.ContextKey).isCompleted()
        ) {
            val vertxTransaction: VertxTransaction = context[VertxTransaction.ContextKey]
            Uni.createFrom().item(
                VertxConnection(vertxTransaction.connection, true)
            )
        } else {
            connection
                .map { connection -> VertxConnection(connection, false) }
        }
    }

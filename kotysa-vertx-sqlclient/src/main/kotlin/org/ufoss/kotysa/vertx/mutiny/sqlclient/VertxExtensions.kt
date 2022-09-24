/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import io.smallrye.mutiny.Uni
import io.vertx.mutiny.core.buffer.Buffer
import io.vertx.mutiny.sqlclient.Pool
import io.vertx.mutiny.sqlclient.Row
import io.vertx.mutiny.sqlclient.Tuple
import org.ufoss.kotysa.DbType
import org.ufoss.kotysa.DefaultSqlClientCommon
import org.ufoss.kotysa.RowImpl
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
            val dbValue = tables.getVertxDbValue(parameter)
            tuple.addValue(dbValue)
        }
}

internal fun <T> Tables.getVertxDbValue(value: T) =
    if (value != null && value is ByteArray && dbType == DbType.MYSQL) {
        Buffer.buffer(value)
    } else {
        getDbValue(value)
    }

internal fun Pool.getVertxConnection() =
    // reuse currentTransaction's connection if any, else establish a new connection
    Uni.createFrom().context { context ->
        if (context.contains(VertxTransaction.ContextKey)
            && !context.get<VertxTransaction>(VertxTransaction.ContextKey).isCompleted()) {
            val vertxTransaction: VertxTransaction = context[VertxTransaction.ContextKey]
            Uni.createFrom().item(
                VertxConnection(vertxTransaction.connection, true)
            )
        } else {
            connection
                .map { connection -> VertxConnection(connection, false) }
        }
    }

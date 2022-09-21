/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import io.vertx.mutiny.sqlclient.Row
import io.vertx.mutiny.sqlclient.Tuple
import org.ufoss.kotysa.DefaultSqlClientCommon
import org.ufoss.kotysa.RowImpl

public fun Row.toRow(): RowImpl = RowImpl(VertxRow(this))

internal fun DefaultSqlClientCommon.Properties.vertxBindParams(tuple: Tuple = Tuple.tuple()) {
    parameters
        .flatMap { parameter ->
            if (parameter is Collection<*>) {
                parameter
            } else {
                setOf(parameter)
            }
        }
        .forEach { parameter ->
            val dbValue = tables.getDbValue(parameter)!!
            tuple.addValue(dbValue)
        }
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import java.sql.Connection

internal data class JdbcConnection(
    internal val connection: Connection,
    internal val inTransaction: Boolean,
)

internal inline fun <T : Any> JdbcConnection.execute(block: (Connection) -> T) =
    try {
        block(this.connection)
    } finally {
        if (!inTransaction) {
            connection.close()
        }
    }
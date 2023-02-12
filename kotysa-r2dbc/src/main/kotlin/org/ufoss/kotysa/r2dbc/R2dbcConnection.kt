/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import io.r2dbc.spi.Connection
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

internal data class R2dbcConnection(
    internal val connection: Connection,
    internal val inTransaction: Boolean,
) : AbstractCoroutineContextElement(R2dbcConnection) {

    /**
     * Key for [R2dbcConnection] instance in the coroutine context.
     */
    internal companion object Key : CoroutineContext.Key<R2dbcConnection>
}

internal suspend inline fun <T : Any> R2dbcConnection.execute(block: (Connection) -> T) =
    try {
        block(this.connection)
    } finally {
        if (!inTransaction) {
            connection.close().awaitFirstOrNull()
        }
    }
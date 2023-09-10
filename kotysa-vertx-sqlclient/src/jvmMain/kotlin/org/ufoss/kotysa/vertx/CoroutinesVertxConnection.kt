/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import io.vertx.kotlin.coroutines.await
import io.vertx.sqlclient.SqlConnection
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

internal data class CoroutinesVertxConnection(
    internal val connection: SqlConnection,
    internal val inTransaction: Boolean,
) : AbstractCoroutineContextElement(CoroutinesVertxConnection) {

    /**
     * Key for [CoroutinesVertxConnection] instance in the coroutine context.
     */
    internal companion object Key : CoroutineContext.Key<CoroutinesVertxConnection>
}

internal suspend inline fun <T : Any> CoroutinesVertxConnection.execute(block: (SqlConnection) -> T) =
    try {
        block(this.connection)
    } finally {
        if (!inTransaction) {
            connection.close().await()
        }
    }
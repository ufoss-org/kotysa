/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.vertx.mutiny.sqlclient.SqlConnection

internal data class MutinyVertxConnection(
    internal val connection: SqlConnection,
    internal val inTransaction: Boolean,
)

internal inline fun <T : Any> Uni<MutinyVertxConnection>.executeUni(crossinline block: (SqlConnection) -> Uni<T>): Uni<T> =
    flatMap { vertxConnection ->
        block(vertxConnection.connection)
            .onTermination().call { ->
                if (!vertxConnection.inTransaction) {
                    vertxConnection.connection.close()
                } else {
                    Uni.createFrom().voidItem()
                }
            }
    }

internal inline fun <T : Any> Uni<MutinyVertxConnection>.executeMulti(crossinline block: (SqlConnection) -> Multi<T>): Multi<T> =
    toMulti()
        .flatMap { vertxConnection ->
            block(vertxConnection.connection)
                .onTermination().call { ->
                    if (!vertxConnection.inTransaction) {
                        vertxConnection.connection.close()
                    } else {
                        Uni.createFrom().voidItem()
                    }
                }
        }

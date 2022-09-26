/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.transaction

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni

public interface VertxTransactionalOp {
    public fun <T : Any> transactional(block: (VertxTransaction) -> Uni<T>): Uni<T>
    public fun <T : Any> transactionalMulti(block: (VertxTransaction) -> Multi<T>): Multi<T>
}

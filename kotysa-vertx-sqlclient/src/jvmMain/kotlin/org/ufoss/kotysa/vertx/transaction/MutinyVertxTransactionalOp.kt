/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.transaction

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni

public interface MutinyVertxTransactionalOp {
    public fun <T : Any> transactional(block: (MutinyVertxTransaction) -> Uni<T>): Uni<T>
    public fun <T : Any> transactionalMulti(block: (MutinyVertxTransaction) -> Multi<T>): Multi<T>
}

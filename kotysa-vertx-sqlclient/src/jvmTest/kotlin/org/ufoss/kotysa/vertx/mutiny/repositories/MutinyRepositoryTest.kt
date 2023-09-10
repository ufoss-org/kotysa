/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.vertx.transaction.MutinyVertxTransactionalOp

interface MutinyRepositoryTest<T : Repository> {
    val repository: T
    val operator : MutinyVertxTransactionalOp
}

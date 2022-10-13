/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.transaction.VertxTransactionalOp

interface MutinyRepositoryTest<T : Repository> {
    val repository: T
    val operator : VertxTransactionalOp
}

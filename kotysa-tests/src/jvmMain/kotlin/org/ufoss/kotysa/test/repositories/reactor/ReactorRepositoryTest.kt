/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.transaction.ReactorTransactionalOp
import org.ufoss.kotysa.transaction.Transaction

interface ReactorRepositoryTest<T : Repository, U : Transaction> {
    val repository: T
    val operator : ReactorTransactionalOp<U>
}

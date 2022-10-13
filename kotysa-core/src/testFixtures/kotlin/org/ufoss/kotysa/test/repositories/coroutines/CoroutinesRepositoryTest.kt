/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.transaction.CoroutinesTransactionalOp
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesRepositoryTest<T : Repository, U : Transaction> {
    val repository: T
    val coOperator: CoroutinesTransactionalOp<U>
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.transaction.TransactionalOp

interface RepositoryTest<T : Repository, U : Transaction> {
    val repository: T
    val operator: TransactionalOp<U>
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories

import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.transaction.CoroutinesTransactionalOp

interface CoroutinesRepositoryTest<T : Repository> {
    val repository: T
    val operator: CoroutinesTransactionalOp
}

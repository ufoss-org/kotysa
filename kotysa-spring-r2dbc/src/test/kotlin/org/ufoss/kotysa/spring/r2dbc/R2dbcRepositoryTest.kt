/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.ufoss.kotysa.spring.r2dbc.transaction.CoroutinesTransactionalOp
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransactionalOp
import org.ufoss.kotysa.test.Repository

interface R2dbcRepositoryTest<T : Repository> {
    val repository: T
    val operator : ReactorTransactionalOp
    val coOperator : CoroutinesTransactionalOp
}

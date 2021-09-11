/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.ufoss.kotysa.r2dbc.transaction.*
import org.ufoss.kotysa.test.Repository

interface R2dbcRepositoryTest<T : Repository> {
    val repository: T
    val operator : ReactorTransactionalOp
    val coOperator : CoroutinesTransactionalOp
}

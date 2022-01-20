/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc

import org.ufoss.kotysa.spring.r2dbc.transaction.SpringR2dbcReactorTransactionalOp
import org.ufoss.kotysa.spring.r2dbc.transaction.SpringR2dbcCoroutinesTransactionalOp
import org.ufoss.kotysa.test.Repository

interface R2dbcRepositoryTest<T : Repository> {
    val repository: T
    val operator : SpringR2dbcReactorTransactionalOp
    val coOperator : SpringR2dbcCoroutinesTransactionalOp
}

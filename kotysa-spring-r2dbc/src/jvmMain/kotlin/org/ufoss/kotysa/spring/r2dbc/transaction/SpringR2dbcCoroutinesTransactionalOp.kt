/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.transaction

import kotlinx.coroutines.flow.Flow
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import org.springframework.transaction.reactive.transactional
import org.ufoss.kotysa.transaction.CoroutinesTransactionalOp

/**
 * @see TransactionalOperator
 * @see executeAndAwait
 * @see transactional
 */
public class SpringR2dbcCoroutinesTransactionalOp(internal val operator: TransactionalOperator) :
    CoroutinesTransactionalOp<ReactorTransaction> {
    public override suspend fun <T> transactional(block: suspend (ReactorTransaction) -> T): T? =
        operator.executeAndAwait { reactiveTransaction -> block.invoke(ReactorTransaction(reactiveTransaction)) }
}

/**
 * Create a [SpringR2dbcCoroutinesTransactionalOp] from a Reactive [TransactionalOperator]
 */
public fun TransactionalOperator.coTransactionalOp(): SpringR2dbcCoroutinesTransactionalOp =
    SpringR2dbcCoroutinesTransactionalOp(this)

public fun <T : Any> Flow<T>.transactional(operator: SpringR2dbcCoroutinesTransactionalOp): Flow<T> =
    this.transactional(operator.operator)

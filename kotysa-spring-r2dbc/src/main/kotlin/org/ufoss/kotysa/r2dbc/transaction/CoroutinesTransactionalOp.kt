/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.transaction

import kotlinx.coroutines.flow.Flow
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import org.springframework.transaction.reactive.transactional

/**
 * @see TransactionalOperator
 * @see executeAndAwait
 * @see transactional
 */
public class CoroutinesTransactionalOp(internal val operator: TransactionalOperator) {
    public suspend fun <T : Any> execute(block: suspend (ReactorTransaction) -> T?): T? =
            operator.executeAndAwait { reactiveTransaction -> block.invoke(ReactorTransaction(reactiveTransaction)) }
}

/**
 * Create a [CoroutinesTransactionalOp] from a Reactive [TransactionalOperator]
 */
public fun TransactionalOperator.coTransactionalOp(): CoroutinesTransactionalOp = CoroutinesTransactionalOp(this)

public fun <T : Any> Flow<T>.transactional(operator: CoroutinesTransactionalOp): Flow<T> =
        this.transactional(operator.operator)

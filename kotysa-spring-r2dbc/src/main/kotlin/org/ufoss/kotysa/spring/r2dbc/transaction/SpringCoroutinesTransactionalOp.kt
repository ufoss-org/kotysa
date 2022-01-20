/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.transaction

import kotlinx.coroutines.flow.Flow
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import org.springframework.transaction.reactive.transactional
import org.ufoss.kotysa.transaction.CoroutinesTransactionalOp
import org.ufoss.kotysa.transaction.Transaction

/**
 * @see TransactionalOperator
 * @see executeAndAwait
 * @see transactional
 */
public class SpringCoroutinesTransactionalOp(internal val operator: TransactionalOperator): CoroutinesTransactionalOp {
    public override suspend fun <T> execute(block: suspend (Transaction) -> T): T? =
            operator.executeAndAwait { reactiveTransaction -> block.invoke(ReactorTransaction(reactiveTransaction)) }
}

/**
 * Create a [CoroutinesTransactionalOp] from a Reactive [TransactionalOperator]
 */
public fun TransactionalOperator.coTransactionalOp(): CoroutinesTransactionalOp = SpringCoroutinesTransactionalOp(this)

public fun <T : Any> Flow<T>.transactional(operator: SpringCoroutinesTransactionalOp): Flow<T> =
        this.transactional(operator.operator)

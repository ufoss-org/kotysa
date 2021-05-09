/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.transaction

import org.reactivestreams.Publisher
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @see org.springframework.transaction.reactive.TransactionalOperator
 */
@JvmInline
public value class ReactorTransactionalOp(internal val operator: TransactionalOperator) {
    public fun <T : Any> execute(block: (ReactorTransaction) -> Publisher<T?>): Flux<T?> =
        operator.execute { reactiveTransaction -> block.invoke(ReactorTransaction(reactiveTransaction)) }
}

/**
 * Create a [ReactorTransactionalOp] from a Reactive [TransactionalOperator]
 */
public fun TransactionalOperator.transactionalOp(): ReactorTransactionalOp = ReactorTransactionalOp(this)

public fun <T : Any> Mono<T>.transactional(operator: ReactorTransactionalOp): Mono<T> =
        operator.operator.transactional(this)

public fun <T : Any> Flux<T>.transactional(operator: ReactorTransactionalOp): Flux<T> =
        operator.operator.transactional(this)

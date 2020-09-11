/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.reactivestreams.Publisher
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @see org.springframework.transaction.reactive.TransactionalOperator
 */
public class ReactorTransactionalOp(private val operator: TransactionalOperator) {

    public fun <T> transactional(flux: Flux<T>): Flux<T> = execute { flux }

    public fun <T> transactional(mono: Mono<T>): Mono<T> = operator.transactional(mono)

    public fun <T> execute(block: (ReactorTransaction) -> Publisher<T>): Flux<T> =
        operator.execute { reactiveTransaction -> block.invoke(ReactorTransaction(reactiveTransaction)) }
}

/**
 * Create a [ReactorTransactionalOp] from a Reactive [TransactionalOperator]
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public fun TransactionalOperator.transactionalOp(): ReactorTransactionalOp = ReactorTransactionalOp(this)

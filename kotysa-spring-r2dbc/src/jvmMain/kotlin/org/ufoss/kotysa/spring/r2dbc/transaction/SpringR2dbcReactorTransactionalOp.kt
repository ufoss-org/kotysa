/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.transaction

import org.reactivestreams.Publisher
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.transaction.ReactorTransactionalOp
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @see org.springframework.transaction.reactive.TransactionalOperator
 */
public class SpringR2dbcReactorTransactionalOp(internal val operator: TransactionalOperator) :
    ReactorTransactionalOp<ReactorTransaction> {
    public override fun <T : Any> transactional(block: (ReactorTransaction) -> Publisher<T?>): Flux<T?> =
        operator.execute { reactiveTransaction -> block.invoke(ReactorTransaction(reactiveTransaction)) }
}

/**
 * Create a [SpringR2dbcReactorTransactionalOp] from a Reactive [TransactionalOperator]
 */
public fun TransactionalOperator.transactionalOp(): SpringR2dbcReactorTransactionalOp =
    SpringR2dbcReactorTransactionalOp(this)

public fun <T : Any> Mono<T>.transactional(operator: SpringR2dbcReactorTransactionalOp): Mono<T> =
    operator.operator.transactional(this)

public fun <T : Any> Flux<T>.transactional(operator: SpringR2dbcReactorTransactionalOp): Flux<T> =
    operator.operator.transactional(this)

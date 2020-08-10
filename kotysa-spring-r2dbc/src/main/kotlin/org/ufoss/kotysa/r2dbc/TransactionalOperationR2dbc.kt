/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

public class TransactionalOperationR2dbc(private val operator: TransactionalOperator) : ReactorTransactionalOperation {
    override fun <T> transactional(mono: Mono<T>): Mono<T> = operator.transactional(mono)

    override fun <T> execute(block: (TransactionStatus) -> Flux<T>): Flux<T> =
        operator.execute { reactiveTransaction -> block.invoke(TransactionStatusR2dbc(reactiveTransaction)) }
}

/**
 * Create a [ReactorTransactionalOperation] from a Reactive [TransactionalOperator]
 *
 * @sample org.ufoss.kotysa.r2dbc.sample.UserRepositoryR2dbc
 */
public fun TransactionalOperator.transactionalOperation(): ReactorTransactionalOperation = TransactionalOperationR2dbc(this)

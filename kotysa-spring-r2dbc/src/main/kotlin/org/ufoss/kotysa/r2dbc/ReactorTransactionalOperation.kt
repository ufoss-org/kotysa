/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * @see org.springframework.transaction.reactive.TransactionalOperator
 */
public interface ReactorTransactionalOperation {

    public fun <T> transactional(flux: Flux<T>): Flux<T> = execute { flux }

    public fun <T> transactional(mono: Mono<T>): Mono<T>

    public fun <T> execute(block: (TransactionStatus) -> Flux<T>): Flux<T>
}

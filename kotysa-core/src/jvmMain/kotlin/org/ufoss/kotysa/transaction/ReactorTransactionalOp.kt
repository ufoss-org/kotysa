/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.transaction

import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

public interface ReactorTransactionalOp<T : Transaction> {
    public fun <U : Any> transactional(block: (T) -> Publisher<U?>): Flux<U?>
}
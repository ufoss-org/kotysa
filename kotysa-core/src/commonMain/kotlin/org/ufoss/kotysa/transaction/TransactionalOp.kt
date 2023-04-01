/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.transaction

public interface TransactionalOp<T : Transaction> {
    public fun <U> transactional(block: (T) -> U): U?
}

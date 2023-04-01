/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.transaction

public interface CoroutinesTransactionalOp<T : Transaction> {
    public suspend fun <U> transactional(block: suspend (T) -> U): U?
}

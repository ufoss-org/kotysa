/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.transaction

public interface CoroutinesTransactionalOp {
    public suspend fun <T> execute(block: suspend (Transaction) -> T): T?
}

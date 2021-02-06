/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.transaction

public interface TransactionalOp {
    public fun <T> execute(block: (Transaction) -> T): T?
}

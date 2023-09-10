/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.transaction

import io.vertx.sqlclient.SqlConnection
import org.ufoss.kotysa.transaction.Transaction
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

public class CoroutinesVertxTransaction(internal val connection: SqlConnection) : Transaction,
    AbstractCoroutineContextElement(CoroutinesVertxTransaction) {

    /**
     * Key for [CoroutinesVertxTransaction] instance in the coroutine context.
     */
    public companion object Key : CoroutineContext.Key<CoroutinesVertxTransaction>

    private var rollbackOnly = false
    private var completed = false

    override fun isNewTransaction(): Boolean = true

    override fun setRollbackOnly() {
        rollbackOnly = true
    }

    override fun isRollbackOnly(): Boolean = rollbackOnly

    override fun isCompleted(): Boolean = completed
    
    internal fun setCompleted() {
        completed = true
    }
}

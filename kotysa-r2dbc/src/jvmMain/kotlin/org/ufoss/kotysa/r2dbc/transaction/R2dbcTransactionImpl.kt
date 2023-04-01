/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.transaction

import io.r2dbc.spi.Connection
import kotlinx.coroutines.reactive.awaitSingle
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.transaction.Transaction
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

public class R2dbcTransactionImpl(internal val connection: Connection) : Transaction,
    AbstractCoroutineContextElement(R2dbcTransactionImpl), R2dbcTransaction {

    /**
     * Key for [R2dbcTransaction] instance in the coroutine context.
     */
    public companion object Key : CoroutineContext.Key<R2dbcTransactionImpl>

    private var rollbackOnly = false
    private var completed = false

    override fun isNewTransaction(): Boolean = true

    override fun setRollbackOnly() {
        rollbackOnly = true
    }

    override fun isRollbackOnly(): Boolean = rollbackOnly

    override fun isCompleted(): Boolean = completed

    override suspend fun createSavepoint(name: String) {
        connection.createSavepoint(name).awaitSingle()
    }

    override suspend fun rollbackToSavepoint(name: String) {
        connection.rollbackTransactionToSavepoint(name).awaitSingle()
    }

    override suspend fun releaseSavepoint(name: String) {
        connection.releaseSavepoint(name).awaitSingle()
    }
    
    internal fun setCompleted() {
        completed = true
    }
}

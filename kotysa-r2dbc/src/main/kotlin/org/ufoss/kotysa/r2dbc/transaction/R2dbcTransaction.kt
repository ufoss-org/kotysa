/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.transaction

import io.r2dbc.spi.Connection
import kotlinx.coroutines.reactive.awaitSingle
import org.ufoss.kotysa.transaction.Transaction

public class R2dbcTransaction(private val connection: Connection) : Transaction {

    private var rollbackOnly = false
    private var completed = false

    override fun isNewTransaction(): Boolean = true

    override fun setRollbackOnly() {
        rollbackOnly = true
    }

    override fun isRollbackOnly(): Boolean = rollbackOnly

    override fun isCompleted(): Boolean = completed

    public suspend fun createSavepoint(name: String) {
        connection.createSavepoint(name).awaitSingle()
    }

    public suspend fun rollbackToSavepoint(name: String) {
        connection.rollbackTransactionToSavepoint(name).awaitSingle()
    }

    public suspend fun releaseSavepoint(name: String) {
        connection.releaseSavepoint(name).awaitSingle()
    }
    
    internal fun setCompleted() {
        completed = true
    }
}

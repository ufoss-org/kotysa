/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.transaction

import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import java.sql.Connection
import java.sql.Savepoint

internal class JdbcTransactionImpl internal constructor(internal val connection: Connection) : JdbcTransaction {

    private var rollbackOnly = false
    private var completed = false

    override fun isNewTransaction(): Boolean = true

    override fun setRollbackOnly() {
        rollbackOnly = true
    }

    override fun isRollbackOnly(): Boolean = rollbackOnly

    override fun isCompleted(): Boolean = completed

    override fun createSavepoint(): Savepoint = connection.setSavepoint()

    override fun rollbackToSavepoint(savepoint: Savepoint) {
        connection.rollback(savepoint)
    }

    override fun releaseSavepoint(savepoint: Savepoint) {
        connection.releaseSavepoint(savepoint)
    }
    
    internal fun setCompleted() {
        completed = true
    }
}

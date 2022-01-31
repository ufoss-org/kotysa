/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.transaction

import org.ufoss.kotysa.transaction.Transaction
import java.sql.Connection
import java.sql.Savepoint

public class JdbcTransaction(internal val connection: Connection) : Transaction {

    private var rollbackOnly = false
    private var completed = false

    override fun isNewTransaction(): Boolean = true

    override fun setRollbackOnly() {
        rollbackOnly = true
    }

    override fun isRollbackOnly(): Boolean = rollbackOnly

    override fun isCompleted(): Boolean = completed

    public fun createSavepoint(): Savepoint = connection.setSavepoint()

    public fun rollbackToSavepoint(savepoint: Savepoint) {
        connection.rollback(savepoint)
    }

    public fun releaseSavepoint(savepoint: Savepoint) {
        connection.releaseSavepoint(savepoint)
    }
    
    internal fun setCompleted() {
        completed = true
    }
}

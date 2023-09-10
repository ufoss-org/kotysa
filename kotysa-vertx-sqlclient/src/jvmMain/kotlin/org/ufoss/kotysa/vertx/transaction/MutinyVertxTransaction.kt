/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.transaction

import io.vertx.mutiny.sqlclient.SqlConnection
import org.ufoss.kotysa.transaction.Transaction

public class MutinyVertxTransaction internal constructor(internal val connection: SqlConnection) : Transaction {

    internal companion object {
        /**
         * Key for [MutinyVertxTransaction] instance in the Mutiny context.
         */
        const val ContextKey = "VertxTransaction"
    }
    
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

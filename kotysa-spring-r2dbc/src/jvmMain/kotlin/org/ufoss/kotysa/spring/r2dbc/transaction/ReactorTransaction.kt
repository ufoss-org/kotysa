/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.transaction

import org.springframework.transaction.ReactiveTransaction
import org.ufoss.kotysa.transaction.Transaction

public class ReactorTransaction(private val reactiveTransaction: ReactiveTransaction) : Transaction {

    override fun isNewTransaction(): Boolean = reactiveTransaction.isNewTransaction

    override fun setRollbackOnly() {
        reactiveTransaction.setRollbackOnly()
    }

    override fun isRollbackOnly(): Boolean = reactiveTransaction.isRollbackOnly

    override fun isCompleted(): Boolean = reactiveTransaction.isCompleted
}

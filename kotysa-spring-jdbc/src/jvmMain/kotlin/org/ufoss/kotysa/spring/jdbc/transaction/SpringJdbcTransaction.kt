/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.transaction

import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.transaction.Transaction

public class SpringJdbcTransaction(
    private val transactionStatus: TransactionStatus,
    private val template: TransactionTemplate,
) : Transaction {

    override fun isNewTransaction(): Boolean = transactionStatus.isNewTransaction

    override fun setRollbackOnly() {
        transactionStatus.setRollbackOnly()
    }

    override fun isRollbackOnly(): Boolean = transactionStatus.isRollbackOnly

    override fun isCompleted(): Boolean = transactionStatus.isCompleted

    public fun hasSavepoint(): Boolean = transactionStatus.hasSavepoint()

    public fun flush() {
        transactionStatus.flush()
    }

    public fun createSavepoint(): Any = transactionStatus.createSavepoint()

    public fun rollbackToSavepoint(savepoint: Any) {
        transactionStatus.rollbackToSavepoint(savepoint)
    }

    public fun releaseSavepoint(savepoint: Any) {
        transactionStatus.releaseSavepoint(savepoint)
    }
    
    public var readOnly: Boolean
        get() = template.isReadOnly
        set(value) {
            template.isReadOnly = value
        }
}

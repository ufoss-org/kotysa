/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.transaction

import org.springframework.transaction.support.TransactionOperations
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.transaction.TransactionalOp

public inline class JdbcTransactionalOp(private val template: TransactionOperations) : TransactionalOp {
    override fun <T> execute(block: (Transaction) -> T): T? =
            template.execute { transactionStatus -> block.invoke(JdbcTransaction(transactionStatus)) }
}

/**
 * Create a [TransactionalOp] from a [TransactionOperations]
 */
public fun TransactionOperations.transactionalOp(): TransactionalOp = JdbcTransactionalOp(this)

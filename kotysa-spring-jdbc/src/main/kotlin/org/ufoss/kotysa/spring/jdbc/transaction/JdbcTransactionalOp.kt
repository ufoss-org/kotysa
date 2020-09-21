/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.transaction

import org.springframework.transaction.support.TransactionOperations

public inline class JdbcTransactionalOp(private val template: TransactionOperations) {
    public fun <T> execute(block: (JdbcTransaction) -> T): T? =
            template.execute { transactionStatus -> block.invoke(JdbcTransaction(transactionStatus)) }
}

/**
 * Create a [JdbcTransactionalOp] from a [TransactionOperations]
 */
public fun TransactionOperations.transactionalOp(): JdbcTransactionalOp = JdbcTransactionalOp(this)

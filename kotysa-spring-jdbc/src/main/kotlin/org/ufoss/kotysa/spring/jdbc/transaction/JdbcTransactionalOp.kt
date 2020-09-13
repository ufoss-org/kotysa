/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.transaction

import org.springframework.transaction.support.TransactionTemplate

public inline class JdbcTransactionalOp(private val template: TransactionTemplate) {
    public fun <T> execute(block: (JdbcTransaction) -> T): T? =
            template.execute { transactionStatus -> block.invoke(JdbcTransaction(transactionStatus)) }
}

/**
 * Create a [JdbcTransactionalOp] from a [TransactionTemplate]
 */
public fun TransactionTemplate.transactionalOp(): JdbcTransactionalOp = JdbcTransactionalOp(this)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.transaction

import org.springframework.transaction.support.TransactionOperations
import org.ufoss.kotysa.transaction.TransactionalOp

public class SpringJdbcTransactionalOp(private val template: TransactionOperations) : TransactionalOp<SpringJdbcTransaction> {
    public override fun <T> transactional(block: (SpringJdbcTransaction) -> T): T? =
            template.execute { transactionStatus -> block.invoke(SpringJdbcTransaction(transactionStatus)) }
}

/**
 * Create a [SpringJdbcTransactionalOp] from a [TransactionOperations]
 */
public fun TransactionOperations.transactionalOp(): SpringJdbcTransactionalOp = SpringJdbcTransactionalOp(this)

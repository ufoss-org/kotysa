/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.transaction

import org.ufoss.kotysa.transaction.TransactionalOp
import java.lang.reflect.UndeclaredThrowableException
import java.sql.Connection

@JvmInline
public value class JdbcTransactionalOp(private val connection: Connection) : TransactionalOp<JdbcTransaction> {

    public override fun <T> execute(block: (JdbcTransaction) -> T): T? = connection.run {
        val transaction = JdbcTransaction(this)
        autoCommit = false // default true

        try {
            val result = try {
                block.invoke(transaction)
            } catch (ex: RuntimeException) {
                // Transactional code threw application exception -> rollback
                connection.rollback()
                throw ex
            } catch (ex: Error) {
                connection.rollback()
                throw ex
            } catch (ex: Throwable) {
                // Transactional code threw unexpected exception -> rollback
                connection.rollback()
                throw UndeclaredThrowableException(ex, "block threw undeclared checked exception")
            }

            if (transaction.isRollbackOnly()) {
                connection.rollback()
            } else {
                commit()
            }

            result
        } finally {
            transaction.setCompleted()
            autoCommit = true // back to default true
        }
    }
}

/**
 * Create a [JdbcTransactionalOp] from a [Connection]
 */
public fun Connection.transactionalOp(): JdbcTransactionalOp = JdbcTransactionalOp(this)

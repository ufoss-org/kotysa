/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.transaction

import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.transaction.TransactionalOp
import java.lang.reflect.UndeclaredThrowableException
import java.sql.Connection

@JvmInline
public value class JdbcTransactionalOp(private val connection: Connection) : TransactionalOp {

    public override fun <T> execute(block: (Transaction) -> T): T? = connection.run {
        val transaction = JdbcTransaction(this)
        autoCommit = false // default true
        
        val result = try {
            block.invoke(transaction)
        } catch (ex: RuntimeException) {
            // Transactional code threw application exception -> rollback
            rollback(connection, transaction)
            throw ex
        } catch (ex: Error) {
            rollback(connection, transaction)
            throw ex
        } catch (ex: Throwable) {
            // Transactional code threw unexpected exception -> rollback
            rollback(connection, transaction)
            throw UndeclaredThrowableException(ex, "block threw undeclared checked exception")
        }
        
        if (transaction.isRollbackOnly()) {
            rollback(connection, transaction)
        } else {
            commit()
            transaction.setCompleted()
        }
        
        autoCommit = true // back to default true
        
        result
    }
    
    private fun rollback(connection: Connection, transaction: JdbcTransaction) {
        connection.rollback()
        transaction.setCompleted()
    }
}

/**
 * Create a [TransactionalOp] from a [Connection]
 */
public fun Connection.transactionalOp(): TransactionalOp = JdbcTransactionalOp(this)

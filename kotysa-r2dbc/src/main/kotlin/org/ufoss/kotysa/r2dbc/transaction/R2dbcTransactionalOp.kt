/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.transaction

import io.r2dbc.spi.Connection
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.ufoss.kotysa.transaction.CoroutinesTransactionalOp
import java.lang.reflect.UndeclaredThrowableException

@JvmInline
public value class R2dbcTransactionalOp(private val connection: Connection) : CoroutinesTransactionalOp<R2dbcTransaction> {

    public override suspend fun <T> execute(block: suspend (R2dbcTransaction) -> T): T? = connection.run {
        val transaction = R2dbcTransaction(this)
        setAutoCommit(false).awaitFirstOrNull() // default true
        
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
            commitTransaction().awaitFirstOrNull()
            transaction.setCompleted()
        }

        setAutoCommit(true).awaitFirstOrNull() // back to default true
        
        result
    }
    
    private suspend fun rollback(connection: Connection, transaction: R2dbcTransaction) {
        connection.rollbackTransaction().awaitFirstOrNull()
        transaction.setCompleted()
    }
}

/**
 * Create a [R2dbcTransactionalOp] from a [Connection]
 */
public fun Connection.transactionalOp(): R2dbcTransactionalOp = R2dbcTransactionalOp(this)

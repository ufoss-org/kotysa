/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android.transaction

import android.database.sqlite.SQLiteDatabase
import org.ufoss.kotysa.transaction.TransactionalOp

@JvmInline
public value class AndroidTransactionalOp(private val client: SQLiteDatabase) : TransactionalOp<AndroidTransaction> {

    public override fun <T> transactional(block: (AndroidTransaction) -> T): T? =
        client.run {
            val transaction = AndroidTransaction()
            beginTransaction()
            try {
                block.invoke(transaction).also {
                    if (!transaction.isRollbackOnly()) {
                        setTransactionSuccessful()
                    }
                }
            } finally {
                endTransaction()
            }
        }
}

/**
 * Create a [AndroidTransactionalOp] from a [SQLiteDatabase]
 */
public fun SQLiteDatabase.transactionalOp(): AndroidTransactionalOp = AndroidTransactionalOp(this)

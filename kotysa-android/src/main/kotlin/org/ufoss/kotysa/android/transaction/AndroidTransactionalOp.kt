/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android.transaction

import android.database.sqlite.SQLiteDatabase

public inline class AndroidTransactionalOp(private val client: SQLiteDatabase) {

    public fun <T> execute(block: (AndroidTransaction) -> T): T? = client.run {
        val transaction = AndroidTransaction(this)
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

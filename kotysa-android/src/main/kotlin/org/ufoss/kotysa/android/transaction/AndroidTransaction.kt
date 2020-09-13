/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android.transaction

import android.database.sqlite.SQLiteDatabase
import org.ufoss.kotysa.transaction.Transaction

public class AndroidTransaction internal constructor(private val client: SQLiteDatabase) : Transaction {

    private var rollbackOnly = false

    override fun isNewTransaction(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setRollbackOnly() {
        rollbackOnly = true
    }

    override fun isRollbackOnly(): Boolean = rollbackOnly

    override fun isCompleted(): Boolean = !client.isOpen
}

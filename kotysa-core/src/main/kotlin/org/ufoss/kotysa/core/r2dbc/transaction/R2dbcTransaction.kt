/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.core.r2dbc.transaction

import org.ufoss.kotysa.transaction.Transaction

public interface R2dbcTransaction : Transaction {
    public suspend fun createSavepoint(name: String)
    public suspend fun rollbackToSavepoint(name: String)
    public suspend fun releaseSavepoint(name: String)
}

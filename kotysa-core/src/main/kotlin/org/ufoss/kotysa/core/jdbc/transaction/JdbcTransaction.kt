/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.core.jdbc.transaction

import org.ufoss.kotysa.transaction.Transaction
import java.sql.Savepoint

public interface JdbcTransaction : Transaction {
    public fun createSavepoint(): Savepoint
    public fun rollbackToSavepoint(savepoint: Savepoint)
    public fun releaseSavepoint(savepoint: Savepoint)
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.transaction.TransactionalOp
import java.sql.Connection

interface JdbcRepositoryTest<T : Repository> {
    var connection: Connection
    var repository: T

    val operator: TransactionalOp
        get() = connection.transactionalOp()
}

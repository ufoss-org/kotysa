/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest
import java.sql.SQLIntegrityConstraintViolationException

@Order(3)
class JdbcInsertMariadbTest : AbstractJdbcMariadbTest<RepositoryMariadbInsert>(),
    InsertTest<MariadbInts, MariadbLongs, MariadbCustomers, RepositoryMariadbInsert, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = RepositoryMariadbInsert(sqlClient)
    override val exceptionClass = SQLIntegrityConstraintViolationException::class
}

class RepositoryMariadbInsert(sqlClient: JdbcSqlClient) :
    InsertRepository<MariadbInts, MariadbLongs, MariadbCustomers>(
        sqlClient,
        MariadbInts,
        MariadbLongs,
        MariadbCustomers
    )

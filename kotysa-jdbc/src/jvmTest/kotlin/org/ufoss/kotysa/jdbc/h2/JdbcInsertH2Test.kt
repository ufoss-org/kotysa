/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest

@Order(3)
class JdbcInsertH2Test : AbstractJdbcH2Test<RepositoryH2Insert>(),
    InsertTest<H2Ints, H2Longs, H2Customers, RepositoryH2Insert, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = RepositoryH2Insert(sqlClient)
    override val exceptionClass = JdbcSQLIntegrityConstraintViolationException::class
}

class RepositoryH2Insert(sqlClient: JdbcSqlClient) :
    InsertRepository<H2Ints, H2Longs, H2Customers>(sqlClient, H2Ints, H2Longs, H2Customers)

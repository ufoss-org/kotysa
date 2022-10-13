/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.junit.jupiter.api.Order
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest

@Order(3)
class SpringJdbcInsertH2Test : AbstractSpringJdbcH2Test<RepositoryH2Insert>(),
    InsertTest<H2Ints, H2Longs, H2Customers, RepositoryH2Insert, SpringJdbcTransaction> {
    override val context = startContext<RepositoryH2Insert>()
    override val repository = getContextRepository<RepositoryH2Insert>()
    override val exceptionClass = DataIntegrityViolationException::class.java
}

class RepositoryH2Insert(dbClient: JdbcOperations) :
    InsertRepository<H2Ints, H2Longs, H2Customers>(dbClient.sqlClient(h2Tables), H2Ints, H2Longs, H2Customers)

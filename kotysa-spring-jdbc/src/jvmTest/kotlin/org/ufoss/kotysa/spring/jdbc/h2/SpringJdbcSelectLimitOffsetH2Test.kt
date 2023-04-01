/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class SpringJdbcSelectLimitOffsetH2Test : AbstractSpringJdbcH2Test<LimitOffsetRepositoryH2Select>(),
    SelectLimitOffsetTest<H2Customers, LimitOffsetRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = LimitOffsetRepositoryH2Select(jdbcOperations)
}

class LimitOffsetRepositoryH2Select(client: JdbcOperations) :
    SelectLimitOffsetRepository<H2Customers>(client.sqlClient(h2Tables), H2Customers)

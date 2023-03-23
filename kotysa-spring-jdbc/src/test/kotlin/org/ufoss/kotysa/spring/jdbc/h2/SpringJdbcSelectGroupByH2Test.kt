/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class SpringJdbcSelectGroupByH2Test : AbstractSpringJdbcH2Test<GroupByRepositoryH2Select>(),
    SelectGroupByTest<H2Customers, GroupByRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = GroupByRepositoryH2Select(jdbcOperations)
}

class GroupByRepositoryH2Select(client: JdbcOperations) :
    SelectGroupByRepository<H2Customers>(client.sqlClient(h2Tables), H2Customers)

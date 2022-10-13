/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class SpringJdbcSelectOrderByH2Test : AbstractSpringJdbcH2Test<OrderByRepositoryH2Select>(),
    SelectOrderByTest<H2Customers, OrderByRepositoryH2Select, SpringJdbcTransaction> {
    override val context = startContext<OrderByRepositoryH2Select>()
    override val repository = getContextRepository<OrderByRepositoryH2Select>()
}

class OrderByRepositoryH2Select(client: JdbcOperations) :
    SelectOrderByRepository<H2Customers>(client.sqlClient(h2Tables), H2Customers)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class SpringJdbcSelectMinMaxAvgSumH2Test : AbstractSpringJdbcH2Test<MinMaxAvgSumRepositoryH2Select>(),
    SelectMinMaxAvgSumTest<H2Customers, MinMaxAvgSumRepositoryH2Select, SpringJdbcTransaction> {
    override val context = startContext<MinMaxAvgSumRepositoryH2Select>()
    override val repository = getContextRepository<MinMaxAvgSumRepositoryH2Select>()
}

class MinMaxAvgSumRepositoryH2Select(client: JdbcOperations) :
    SelectMinMaxAvgSumRepository<H2Customers>(client.sqlClient(h2Tables), H2Customers)

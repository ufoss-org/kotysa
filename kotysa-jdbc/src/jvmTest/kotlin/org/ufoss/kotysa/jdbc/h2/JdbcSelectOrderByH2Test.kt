/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class JdbcSelectOrderByH2Test : AbstractJdbcH2Test<OrderByRepositoryH2Select>(),
    SelectOrderByTest<H2Customers, OrderByRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = OrderByRepositoryH2Select(sqlClient)
}

class OrderByRepositoryH2Select(sqlClient: JdbcSqlClient) : SelectOrderByRepository<H2Customers>(sqlClient, H2Customers)
        

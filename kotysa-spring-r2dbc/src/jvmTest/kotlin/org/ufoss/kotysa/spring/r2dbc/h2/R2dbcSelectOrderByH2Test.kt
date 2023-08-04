/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrderByTest

class R2dbcSelectOrderByH2Test : AbstractR2dbcH2Test<OrderByRepositoryH2Select>(),
    ReactorSelectOrderByTest<H2Customers, OrderByRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        OrderByRepositoryH2Select(sqlClient)
}

class OrderByRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectOrderByRepository<H2Customers>(sqlClient, H2Customers)

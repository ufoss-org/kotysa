/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByTest

class R2dbcSelectOrderByH2Test : AbstractR2dbcH2Test<OrderByRepositoryH2Select>(),
    CoroutinesSelectOrderByTest<H2Customers, OrderByRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = OrderByRepositoryH2Select(sqlClient)
}

class OrderByRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectOrderByRepository<H2Customers>(sqlClient, H2Customers)

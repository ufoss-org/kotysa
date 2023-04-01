/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumTest

class R2dbcSelectMinMaxAvgSumH2Test : AbstractR2dbcH2Test<MinMaxAvgSumRepositoryH2Select>(),
    CoroutinesSelectMinMaxAvgSumTest<H2Customers, MinMaxAvgSumRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = MinMaxAvgSumRepositoryH2Select(sqlClient)
}

class MinMaxAvgSumRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectMinMaxAvgSumRepository<H2Customers>(sqlClient, H2Customers)

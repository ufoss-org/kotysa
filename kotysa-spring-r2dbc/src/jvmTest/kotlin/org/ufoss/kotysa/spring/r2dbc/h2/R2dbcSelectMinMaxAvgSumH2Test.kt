/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectMinMaxAvgSumTest

class R2dbcSelectMinMaxAvgSumH2Test : AbstractR2dbcH2Test<MinMaxAvgSumRepositoryH2Select>(),
    ReactorSelectMinMaxAvgSumTest<H2Customers, MinMaxAvgSumRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        MinMaxAvgSumRepositoryH2Select(sqlClient)
}

class MinMaxAvgSumRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectMinMaxAvgSumRepository<H2Customers>(sqlClient, H2Customers)

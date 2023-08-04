/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetTest

class R2dbcSelectLimitOffsetH2Test : AbstractR2dbcH2Test<LimitOffsetRepositoryH2Select>(),
    ReactorSelectLimitOffsetTest<H2Customers, LimitOffsetRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        LimitOffsetRepositoryH2Select(sqlClient)
}

class LimitOffsetRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectLimitOffsetRepository<H2Customers>(sqlClient, H2Customers)

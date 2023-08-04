/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2LocalTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalTimeTest

class R2dbcSelectLocalTimeH2Test : AbstractR2dbcH2Test<LocalTimeRepositoryH2Select>(),
    ReactorSelectLocalTimeTest<H2LocalTimes, LocalTimeRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        LocalTimeRepositoryH2Select(sqlClient)
}

class LocalTimeRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalTimeRepository<H2LocalTimes>(
        sqlClient,
        H2LocalTimes
    )

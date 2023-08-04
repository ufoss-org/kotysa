/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2LocalDateTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeTest

class R2dbcSelectLocalDateTimeH2Test : AbstractR2dbcH2Test<LocalDateTimeRepositoryH2Select>(),
    ReactorSelectLocalDateTimeTest<H2LocalDateTimes, LocalDateTimeRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        LocalDateTimeRepositoryH2Select(sqlClient)
}

class LocalDateTimeRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateTimeRepository<H2LocalDateTimes>(
        sqlClient,
        H2LocalDateTimes
    )

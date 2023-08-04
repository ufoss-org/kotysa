/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2LocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeAsTimestampTest

class R2dbcSelectLocalDateTimeAsTimestampH2Test :
    AbstractR2dbcH2Test<LocalDateTimeAsTimestampRepositoryH2Select>(),
    ReactorSelectLocalDateTimeAsTimestampTest<H2LocalDateTimeAsTimestamps,
            LocalDateTimeAsTimestampRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        LocalDateTimeAsTimestampRepositoryH2Select(sqlClient)
}

class LocalDateTimeAsTimestampRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateTimeAsTimestampRepository<H2LocalDateTimeAsTimestamps>(
        sqlClient,
        H2LocalDateTimeAsTimestamps
    )

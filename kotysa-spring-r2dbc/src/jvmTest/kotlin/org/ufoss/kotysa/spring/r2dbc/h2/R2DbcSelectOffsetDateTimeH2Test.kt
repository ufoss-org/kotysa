/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2OffsetDateTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOffsetDateTimeTest

class R2dbcSelectOffsetDateTimeH2Test : AbstractR2dbcH2Test<OffsetDateTimeRepositoryH2Select>(),
    ReactorSelectOffsetDateTimeTest<H2OffsetDateTimes, OffsetDateTimeRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        OffsetDateTimeRepositoryH2Select(sqlClient)
}

class OffsetDateTimeRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectOffsetDateTimeRepository<H2OffsetDateTimes>(sqlClient, H2OffsetDateTimes)

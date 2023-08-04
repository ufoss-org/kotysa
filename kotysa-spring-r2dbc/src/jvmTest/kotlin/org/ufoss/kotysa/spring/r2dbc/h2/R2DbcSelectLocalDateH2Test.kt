/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2LocalDates
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTest

class R2dbcSelectLocalDateH2Test : AbstractR2dbcH2Test<LocalDateRepositoryH2Select>(),
    ReactorSelectLocalDateTest<H2LocalDates, LocalDateRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        LocalDateRepositoryH2Select(sqlClient)
}

class LocalDateRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateRepository<H2LocalDates>(sqlClient, H2LocalDates)

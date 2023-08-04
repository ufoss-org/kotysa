/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeTest

class R2dbcSelectKotlinxLocalDateTimeH2Test :
    AbstractR2dbcH2Test<KotlinxLocalDateTimeRepositoryH2Select>(),
    ReactorSelectKotlinxLocalDateTimeTest<H2KotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryH2Select,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        KotlinxLocalDateTimeRepositoryH2Select(sqlClient)
}

class KotlinxLocalDateTimeRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateTimeRepository<H2KotlinxLocalDateTimes>(
        sqlClient,
        H2KotlinxLocalDateTimes
    )

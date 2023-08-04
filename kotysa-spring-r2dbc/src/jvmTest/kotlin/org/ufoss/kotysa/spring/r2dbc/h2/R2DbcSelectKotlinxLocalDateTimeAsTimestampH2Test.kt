/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeAsTimestampTest

class R2dbcSelectKotlinxLocalDateTimeAsTimestampH2Test :
    AbstractR2dbcH2Test<KotlinxLocalDateTimeAsTimestampRepositoryH2Select>(),
    ReactorSelectKotlinxLocalDateTimeAsTimestampTest<H2KotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        KotlinxLocalDateTimeAsTimestampRepositoryH2Select(sqlClient)
}

class KotlinxLocalDateTimeAsTimestampRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateTimeAsTimestampRepository<H2KotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        H2KotlinxLocalDateTimeAsTimestamps
    )

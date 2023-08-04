/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalDates
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTest

class R2dbcSelectKotlinxLocalDateH2Test :
    AbstractR2dbcH2Test<KotlinxLocalDateRepositoryH2Select>(),
    ReactorSelectKotlinxLocalDateTest<H2KotlinxLocalDates, KotlinxLocalDateRepositoryH2Select,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        KotlinxLocalDateRepositoryH2Select(sqlClient)
}

class KotlinxLocalDateRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateRepository<H2KotlinxLocalDates>(sqlClient, H2KotlinxLocalDates)

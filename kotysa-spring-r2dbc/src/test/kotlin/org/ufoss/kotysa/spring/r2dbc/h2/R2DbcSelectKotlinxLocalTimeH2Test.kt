/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeTest

class R2DbcSelectKotlinxLocalTimeH2Test : AbstractR2dbcH2Test<KotlinxLocalTimeH2Repository>(),
    ReactorSelectKotlinxLocalTimeTest<H2KotlinxLocalTimes, KotlinxLocalTimeH2Repository, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        KotlinxLocalTimeH2Repository(sqlClient)
}

class KotlinxLocalTimeH2Repository(sqlClient: H2ReactorSqlClient) :
    ReactorSelectKotlinxLocalTimeRepository<H2KotlinxLocalTimes>(sqlClient, H2KotlinxLocalTimes)

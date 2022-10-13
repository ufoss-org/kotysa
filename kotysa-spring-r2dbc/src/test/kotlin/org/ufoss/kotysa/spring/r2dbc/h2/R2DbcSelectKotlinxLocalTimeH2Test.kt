/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalTimes
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeTest

class R2DbcSelectKotlinxLocalTimeH2Test : AbstractR2dbcH2Test<KotlinxLocalTimeH2Repository>(),
    ReactorSelectKotlinxLocalTimeTest<H2KotlinxLocalTimes, KotlinxLocalTimeH2Repository, ReactorTransaction> {
    override val context = startContext<KotlinxLocalTimeH2Repository>()
    override val repository = getContextRepository<KotlinxLocalTimeH2Repository>()
}

class KotlinxLocalTimeH2Repository(client: DatabaseClient) :
    ReactorSelectKotlinxLocalTimeRepository<H2KotlinxLocalTimes>(client.sqlClient(h2Tables), H2KotlinxLocalTimes)

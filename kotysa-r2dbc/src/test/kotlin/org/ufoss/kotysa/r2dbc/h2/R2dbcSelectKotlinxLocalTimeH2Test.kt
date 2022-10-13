/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeTest

class R2dbcSelectKotlinxLocalTimeH2Test : AbstractR2dbcH2Test<KotlinxLocalTimeRepositoryH2Select>(),
    CoroutinesSelectKotlinxLocalTimeTest<H2KotlinxLocalTimes, KotlinxLocalTimeRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = KotlinxLocalTimeRepositoryH2Select(sqlClient)
}

class KotlinxLocalTimeRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalTimeRepository<H2KotlinxLocalTimes>(sqlClient, H2KotlinxLocalTimes)

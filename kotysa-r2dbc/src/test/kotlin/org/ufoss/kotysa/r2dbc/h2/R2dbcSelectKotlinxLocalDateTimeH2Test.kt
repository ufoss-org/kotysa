/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeTest


class R2dbcSelectKotlinxLocalDateTimeH2Test : AbstractR2dbcH2Test<KotlinxLocalDateTimeRepositoryH2Select>(),
    CoroutinesSelectKotlinxLocalDateTimeTest<H2KotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryH2Select,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = KotlinxLocalDateTimeRepositoryH2Select(sqlClient)
}

class KotlinxLocalDateTimeRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalDateTimeRepository<H2KotlinxLocalDateTimes>(sqlClient, H2KotlinxLocalDateTimes)

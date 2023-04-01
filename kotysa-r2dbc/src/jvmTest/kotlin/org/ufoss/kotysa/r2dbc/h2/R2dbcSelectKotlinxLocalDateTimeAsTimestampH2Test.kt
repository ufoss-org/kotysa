/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeAsTimestampTest


class R2dbcSelectKotlinxLocalDateTimeAsTimestampH2Test :
    AbstractR2dbcH2Test<KotlinxLocalDateTimeAsTimestampRepositoryH2Select>(),
    CoroutinesSelectKotlinxLocalDateTimeAsTimestampTest<H2KotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        KotlinxLocalDateTimeAsTimestampRepositoryH2Select(sqlClient)
}

class KotlinxLocalDateTimeAsTimestampRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalDateTimeAsTimestampRepository<H2KotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        H2KotlinxLocalDateTimeAsTimestamps
    )

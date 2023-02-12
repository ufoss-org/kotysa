/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2LocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeAsTimestampTest


class R2dbcSelectLocalDateTimeAsTimestampH2Test : AbstractR2dbcH2Test<LocalDateTimeAsTimestampRepositoryH2Select>(),
    CoroutinesSelectLocalDateTimeAsTimestampTest<H2LocalDateTimeAsTimestamps, LocalDateTimeAsTimestampRepositoryH2Select,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        LocalDateTimeAsTimestampRepositoryH2Select(sqlClient)
}

class LocalDateTimeAsTimestampRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalDateTimeAsTimestampRepository<H2LocalDateTimeAsTimestamps>(
        sqlClient,
        H2LocalDateTimeAsTimestamps,
    )

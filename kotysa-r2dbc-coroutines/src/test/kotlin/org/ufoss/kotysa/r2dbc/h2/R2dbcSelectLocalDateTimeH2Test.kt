/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2LocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeTest

class R2dbcSelectLocalDateTimeH2Test : AbstractR2dbcH2Test<LocalDateTimeRepositoryH2Select>(),
    CoroutinesSelectLocalDateTimeTest<H2LocalDateTimes, LocalDateTimeRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalDateTimeRepositoryH2Select(sqlClient)
}

class LocalDateTimeRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalDateTimeRepository<H2LocalDateTimes>(sqlClient, H2LocalDateTimes)

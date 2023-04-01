/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2OffsetDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOffsetDateTimeTest

class R2dbcSelectOffsetDateTimeH2Test : AbstractR2dbcH2Test<OffsetDateTimeRepositoryH2Select>(),
    CoroutinesSelectOffsetDateTimeTest<H2OffsetDateTimes, OffsetDateTimeRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = OffsetDateTimeRepositoryH2Select(sqlClient)
}

class OffsetDateTimeRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectOffsetDateTimeRepository<H2OffsetDateTimes>(sqlClient, H2OffsetDateTimes)

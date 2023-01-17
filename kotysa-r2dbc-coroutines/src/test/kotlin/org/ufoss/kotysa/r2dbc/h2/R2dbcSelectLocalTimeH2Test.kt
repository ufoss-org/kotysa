/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2LocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeTest


class R2dbcSelectLocalTimeH2Test : AbstractR2dbcH2Test<LocalTimeRepositoryH2Select>(),
    CoroutinesSelectLocalTimeTest<H2LocalTimes, LocalTimeRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalTimeRepositoryH2Select(sqlClient)
}

class LocalTimeRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalTimeRepository<H2LocalTimes>(sqlClient, H2LocalTimes)

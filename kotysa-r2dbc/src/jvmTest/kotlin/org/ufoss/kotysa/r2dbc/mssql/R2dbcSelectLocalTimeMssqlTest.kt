/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeTest


class R2dbcSelectLocalTimeMssqlTest : AbstractR2dbcMssqlTest<LocalTimeRepositoryMssqlSelect>(),
    CoroutinesSelectLocalTimeTest<MssqlLocalTimes, LocalTimeRepositoryMssqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalTimeRepositoryMssqlSelect(sqlClient)
}

class LocalTimeRepositoryMssqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalTimeRepository<MssqlLocalTimes>(sqlClient, MssqlLocalTimes)

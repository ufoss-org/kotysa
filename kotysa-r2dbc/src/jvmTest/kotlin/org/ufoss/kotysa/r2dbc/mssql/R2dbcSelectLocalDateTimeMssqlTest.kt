/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlLocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeTest

class R2dbcSelectLocalDateTimeMssqlTest : AbstractR2dbcMssqlTest<LocalDateTimeRepositoryMssqlSelect>(),
    CoroutinesSelectLocalDateTimeTest<MssqlLocalDateTimes, LocalDateTimeRepositoryMssqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalDateTimeRepositoryMssqlSelect(sqlClient)
}

class LocalDateTimeRepositoryMssqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalDateTimeRepository<MssqlLocalDateTimes>(sqlClient, MssqlLocalDateTimes)

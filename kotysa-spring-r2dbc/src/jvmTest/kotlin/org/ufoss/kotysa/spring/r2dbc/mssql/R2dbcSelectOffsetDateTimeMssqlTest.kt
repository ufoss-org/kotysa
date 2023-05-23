/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlOffsetDateTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOffsetDateTimeTest

class R2dbcSelectOffsetDateTimeMssqlTest : AbstractR2dbcMssqlTest<OffsetDateTimeRepositoryMssqlSelect>(),
    ReactorSelectOffsetDateTimeTest<MssqlOffsetDateTimes, OffsetDateTimeRepositoryMssqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        OffsetDateTimeRepositoryMssqlSelect(sqlClient)
}

class OffsetDateTimeRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectOffsetDateTimeRepository<MssqlOffsetDateTimes>(sqlClient, MssqlOffsetDateTimes)

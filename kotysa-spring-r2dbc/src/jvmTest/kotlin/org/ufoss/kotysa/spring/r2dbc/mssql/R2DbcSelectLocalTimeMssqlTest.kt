/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlLocalTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalTimeTest

class R2dbcSelectLocalTimeMssqlTest : AbstractR2dbcMssqlTest<LocalTimeRepositoryMssqlSelect>(),
    ReactorSelectLocalTimeTest<MssqlLocalTimes, LocalTimeRepositoryMssqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        LocalTimeRepositoryMssqlSelect(sqlClient)
}

class LocalTimeRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalTimeRepository<MssqlLocalTimes>(
        sqlClient,
        MssqlLocalTimes
    )

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlLocalDateTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeTest

class R2dbcSelectLocalDateTimeMssqlTest : AbstractR2dbcMssqlTest<LocalDateTimeRepositoryMssqlSelect>(),
    ReactorSelectLocalDateTimeTest<MssqlLocalDateTimes, LocalDateTimeRepositoryMssqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        LocalDateTimeRepositoryMssqlSelect(sqlClient)
}

class LocalDateTimeRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateTimeRepository<MssqlLocalDateTimes>(
        sqlClient,
        MssqlLocalDateTimes
    )

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlLocalTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalTimeTest

class R2DbcSelectLocalTimeMssqlTest : AbstractR2dbcMssqlTest<LocalTimeMssqlRepository>(),
    ReactorSelectLocalTimeTest<MssqlLocalTimes, LocalTimeMssqlRepository, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        LocalTimeMssqlRepository(sqlClient)
}

class LocalTimeMssqlRepository(sqlClient: MssqlReactorSqlClient) :
    ReactorSelectLocalTimeRepository<MssqlLocalTimes>(sqlClient, MssqlLocalTimes)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeTest

class R2dbcSelectKotlinxLocalTimeMssqlTest :
    AbstractR2dbcMssqlTest<KotlinxLocalTimeRepositoryMssqlSelect>(),
    ReactorSelectKotlinxLocalTimeTest<MssqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryMssqlSelect,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        KotlinxLocalTimeRepositoryMssqlSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalTimeRepository<MssqlKotlinxLocalTimes>(
        sqlClient,
        MssqlKotlinxLocalTimes
    )

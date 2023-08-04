/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeTest

class R2dbcSelectKotlinxLocalDateTimeMssqlTest :
    AbstractR2dbcMssqlTest<KotlinxLocalDateTimeRepositoryMssqlSelect>(),
    ReactorSelectKotlinxLocalDateTimeTest<MssqlKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMssqlSelect,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        KotlinxLocalDateTimeRepositoryMssqlSelect(sqlClient)
}

class KotlinxLocalDateTimeRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateTimeRepository<MssqlKotlinxLocalDateTimes>(
        sqlClient,
        MssqlKotlinxLocalDateTimes
    )

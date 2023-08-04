/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTest

class R2dbcSelectKotlinxLocalDateMssqlTest :
    AbstractR2dbcMssqlTest<KotlinxLocalDateRepositoryMssqlSelect>(),
    ReactorSelectKotlinxLocalDateTest<MssqlKotlinxLocalDates, KotlinxLocalDateRepositoryMssqlSelect,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        KotlinxLocalDateRepositoryMssqlSelect(sqlClient)
}

class KotlinxLocalDateRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateRepository<MssqlKotlinxLocalDates>(sqlClient, MssqlKotlinxLocalDates)

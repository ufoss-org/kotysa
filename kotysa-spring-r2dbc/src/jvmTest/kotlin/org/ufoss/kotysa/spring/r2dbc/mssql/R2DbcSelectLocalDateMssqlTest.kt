/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlLocalDates
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTest

class R2dbcSelectLocalDateMssqlTest : AbstractR2dbcMssqlTest<LocalDateRepositoryMssqlSelect>(),
    ReactorSelectLocalDateTest<MssqlLocalDates, LocalDateRepositoryMssqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        LocalDateRepositoryMssqlSelect(sqlClient)
}

class LocalDateRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateRepository<MssqlLocalDates>(sqlClient, MssqlLocalDates)

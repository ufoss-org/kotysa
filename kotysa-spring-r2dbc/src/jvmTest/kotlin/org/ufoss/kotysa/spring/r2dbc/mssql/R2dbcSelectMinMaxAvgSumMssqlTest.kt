/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectMinMaxAvgSumTest

class R2dbcSelectMinMaxAvgSumMssqlTest : AbstractR2dbcMssqlTest<MinMaxAvgSumRepositoryMssqlSelect>(),
    ReactorSelectMinMaxAvgSumTest<MssqlCustomers, MinMaxAvgSumRepositoryMssqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        MinMaxAvgSumRepositoryMssqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectMinMaxAvgSumRepository<MssqlCustomers>(sqlClient, MssqlCustomers)

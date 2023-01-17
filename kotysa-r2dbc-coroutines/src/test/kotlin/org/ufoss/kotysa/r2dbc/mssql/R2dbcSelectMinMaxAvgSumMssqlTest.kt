/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumTest

class R2dbcSelectMinMaxAvgSumMssqlTest : AbstractR2dbcMssqlTest<MinMaxAvgSumRepositoryMssqlSelect>(),
    CoroutinesSelectMinMaxAvgSumTest<MssqlCustomers, MinMaxAvgSumRepositoryMssqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = MinMaxAvgSumRepositoryMssqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMssqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectMinMaxAvgSumRepository<MssqlCustomers>(sqlClient, MssqlCustomers)

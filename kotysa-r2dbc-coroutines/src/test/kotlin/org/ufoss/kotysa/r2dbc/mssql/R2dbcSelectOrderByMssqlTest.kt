/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByTest

class R2dbcSelectOrderByMssqlTest : AbstractR2dbcMssqlTest<OrderByRepositoryMssqlSelect>(),
    CoroutinesSelectOrderByTest<MssqlCustomers, OrderByRepositoryMssqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = OrderByRepositoryMssqlSelect(sqlClient)
}

class OrderByRepositoryMssqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectOrderByRepository<MssqlCustomers>(sqlClient, MssqlCustomers)

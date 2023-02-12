/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByTest

class R2dbcSelectGroupByMssqlTest : AbstractR2dbcMssqlTest<GroupByRepositoryMssqlSelect>(),
    CoroutinesSelectGroupByTest<MssqlCustomers, GroupByRepositoryMssqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = GroupByRepositoryMssqlSelect(sqlClient)
}

class GroupByRepositoryMssqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectGroupByRepository<MssqlCustomers>(sqlClient, MssqlCustomers)

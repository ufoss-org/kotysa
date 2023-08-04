/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectGroupByTest

class R2dbcSelectGroupByMssqlTest : AbstractR2dbcMssqlTest<GroupByRepositoryMssqlSelect>(),
    ReactorSelectGroupByTest<MssqlCustomers, GroupByRepositoryMssqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        GroupByRepositoryMssqlSelect(sqlClient)
}

class GroupByRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectGroupByRepository<MssqlCustomers>(sqlClient, MssqlCustomers)

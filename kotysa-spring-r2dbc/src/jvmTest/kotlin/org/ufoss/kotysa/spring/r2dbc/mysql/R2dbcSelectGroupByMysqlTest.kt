/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectGroupByTest

class R2dbcSelectGroupByMysqlTest : AbstractR2dbcMysqlTest<GroupByRepositoryMysqlSelect>(),
    ReactorSelectGroupByTest<MysqlCustomers, GroupByRepositoryMysqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        GroupByRepositoryMysqlSelect(sqlClient)
}

class GroupByRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectGroupByRepository<MysqlCustomers>(sqlClient, MysqlCustomers)

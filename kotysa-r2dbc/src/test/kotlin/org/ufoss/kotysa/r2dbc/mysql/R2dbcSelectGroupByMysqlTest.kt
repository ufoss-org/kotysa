/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByTest

class R2dbcSelectGroupByMysqlTest : AbstractR2dbcMysqlTest<GroupByRepositoryMysqlSelect>(),
    CoroutinesSelectGroupByTest<MysqlCustomers, GroupByRepositoryMysqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = GroupByRepositoryMysqlSelect(sqlClient)
}

class GroupByRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectGroupByRepository<MysqlCustomers>(sqlClient, MysqlCustomers)

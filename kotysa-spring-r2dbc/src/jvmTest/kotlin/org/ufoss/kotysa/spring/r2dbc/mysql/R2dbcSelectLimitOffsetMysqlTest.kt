/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetTest

class R2dbcSelectLimitOffsetMysqlTest : AbstractR2dbcMysqlTest<LimitOffsetRepositoryMysqlSelect>(),
    ReactorSelectLimitOffsetTest<MysqlCustomers, LimitOffsetRepositoryMysqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        LimitOffsetRepositoryMysqlSelect(sqlClient)
}

class LimitOffsetRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLimitOffsetRepository<MysqlCustomers>(sqlClient, MysqlCustomers)

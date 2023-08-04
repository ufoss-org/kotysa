/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectMinMaxAvgSumTest

class R2dbcSelectMinMaxAvgSumMysqlTest : AbstractR2dbcMysqlTest<MinMaxAvgSumRepositoryMysqlSelect>(),
    ReactorSelectMinMaxAvgSumTest<MysqlCustomers, MinMaxAvgSumRepositoryMysqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        MinMaxAvgSumRepositoryMysqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectMinMaxAvgSumRepository<MysqlCustomers>(sqlClient, MysqlCustomers)

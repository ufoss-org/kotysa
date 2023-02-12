/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumTest

class R2dbcSelectMinMaxAvgSumMysqlTest : AbstractR2dbcMysqlTest<MinMaxAvgSumRepositoryMysqlSelect>(),
    CoroutinesSelectMinMaxAvgSumTest<MysqlCustomers, MinMaxAvgSumRepositoryMysqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = MinMaxAvgSumRepositoryMysqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectMinMaxAvgSumRepository<MysqlCustomers>(sqlClient, MysqlCustomers)

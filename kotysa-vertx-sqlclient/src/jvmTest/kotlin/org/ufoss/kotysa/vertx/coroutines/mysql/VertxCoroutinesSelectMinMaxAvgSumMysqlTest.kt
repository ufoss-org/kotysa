/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumTest

class VertxCoroutinesSelectMinMaxAvgSumMysqlTest : AbstractVertxCoroutinesMysqlTest<MinMaxAvgSumRepositoryMysqlSelect>(),
    CoroutinesSelectMinMaxAvgSumTest<MysqlCustomers, MinMaxAvgSumRepositoryMysqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = MinMaxAvgSumRepositoryMysqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectMinMaxAvgSumRepository<MysqlCustomers>(sqlClient, MysqlCustomers)

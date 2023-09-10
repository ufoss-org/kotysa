/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumTest

class VertxCoroutinesSelectMinMaxAvgSumMariadbTest : AbstractVertxCoroutinesMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>(),
    CoroutinesSelectMinMaxAvgSumTest<MariadbCustomers, MinMaxAvgSumRepositoryMariadbSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = MinMaxAvgSumRepositoryMariadbSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectMinMaxAvgSumRepository<MariadbCustomers>(sqlClient, MariadbCustomers)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumTest

class VertxCoroutinesSelectMinMaxAvgSumOracleTest : AbstractVertxCoroutinesOracleTest<MinMaxAvgSumRepositoryOracleSelect>(),
    CoroutinesSelectMinMaxAvgSumTest<OracleCustomers, MinMaxAvgSumRepositoryOracleSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = MinMaxAvgSumRepositoryOracleSelect(sqlClient)
}

class MinMaxAvgSumRepositoryOracleSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectMinMaxAvgSumRepository<OracleCustomers>(sqlClient, OracleCustomers)

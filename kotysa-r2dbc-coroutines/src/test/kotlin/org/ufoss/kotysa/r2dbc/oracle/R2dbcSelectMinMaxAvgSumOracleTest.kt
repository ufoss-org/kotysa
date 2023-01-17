/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumTest

class R2dbcSelectMinMaxAvgSumOracleTest : AbstractR2dbcOracleTest<MinMaxAvgSumRepositoryOracleSelect>(),
    CoroutinesSelectMinMaxAvgSumTest<OracleCustomers, MinMaxAvgSumRepositoryOracleSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = MinMaxAvgSumRepositoryOracleSelect(sqlClient)
}

class MinMaxAvgSumRepositoryOracleSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectMinMaxAvgSumRepository<OracleCustomers>(sqlClient, OracleCustomers)

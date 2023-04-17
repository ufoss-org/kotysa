/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectMinMaxAvgSumTest

class R2dbcSelectMinMaxAvgSumOracleTest : AbstractR2dbcOracleTest<MinMaxAvgSumRepositoryOracleSelect>(),
    ReactorSelectMinMaxAvgSumTest<OracleCustomers, MinMaxAvgSumRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        MinMaxAvgSumRepositoryOracleSelect(sqlClient)
}

class MinMaxAvgSumRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectMinMaxAvgSumRepository<OracleCustomers>(sqlClient, OracleCustomers)

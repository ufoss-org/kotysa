/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectMinMaxAvgSumTest

class JdbcSelectMinMaxAvgSumOracleTest : AbstractR2dbcOracleTest<MinMaxAvgSumRepositoryOracleSelect>(),
    ReactorSelectMinMaxAvgSumTest<OracleCustomers, MinMaxAvgSumRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<MinMaxAvgSumRepositoryOracleSelect>(resource)
    }

    override val repository: MinMaxAvgSumRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class MinMaxAvgSumRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectMinMaxAvgSumRepository<OracleCustomers>(sqlClient, OracleCustomers)

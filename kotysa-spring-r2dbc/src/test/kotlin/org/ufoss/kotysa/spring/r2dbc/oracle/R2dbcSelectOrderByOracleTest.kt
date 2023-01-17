/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrderByTest

class JdbcSelectOrderByOracleTest : AbstractR2dbcOracleTest<OrderByRepositoryOracleSelect>(),
    ReactorSelectOrderByTest<OracleCustomers, OrderByRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OrderByRepositoryOracleSelect>(resource)
    }

    override val repository: OrderByRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class OrderByRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectOrderByRepository<OracleCustomers>(sqlClient, OracleCustomers)

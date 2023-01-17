/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLimitOffsetTest

class JdbcSelectLimitOffsetOracleTest : AbstractR2dbcOracleTest<LimitOffsetRepositoryOracleSelect>(),
    ReactorSelectLimitOffsetTest<OracleCustomers, LimitOffsetRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LimitOffsetRepositoryOracleSelect>(resource)
    }

    override val repository: LimitOffsetRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class LimitOffsetRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLimitOffsetRepository<OracleCustomers>(sqlClient, OracleCustomers)

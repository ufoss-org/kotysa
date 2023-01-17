/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntTest

@Order(1)
class JdbcSelectIntOracleTest : AbstractR2dbcOracleTest<ReactorSelectIntRepositoryOracleSelect>(),
    ReactorSelectIntTest<OracleInts, ReactorSelectIntRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ReactorSelectIntRepositoryOracleSelect>(resource)
    }

    override val repository: ReactorSelectIntRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class ReactorSelectIntRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectIntRepository<OracleInts>(sqlClient, OracleInts)

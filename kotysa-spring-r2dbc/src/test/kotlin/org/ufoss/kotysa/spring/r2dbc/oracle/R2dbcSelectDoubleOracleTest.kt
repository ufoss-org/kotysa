/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleDoubles
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleTest

class JdbcSelectDoubleOracleTest : AbstractR2dbcOracleTest<ReactorSelectDoubleRepositoryOracleSelect>(),
    ReactorSelectDoubleTest<OracleDoubles, ReactorSelectDoubleRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ReactorSelectDoubleRepositoryOracleSelect>(resource)
    }

    override val repository: ReactorSelectDoubleRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class ReactorSelectDoubleRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectDoubleRepository<OracleDoubles>(sqlClient, OracleDoubles)

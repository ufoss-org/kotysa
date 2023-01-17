/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleInheriteds
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceTest

class JdbcInheritanceOracleTest : AbstractR2dbcOracleTest<ReactorInheritanceOracleRepository>(),
    ReactorInheritanceTest<OracleInheriteds, ReactorInheritanceOracleRepository, ReactorTransaction> {
    override val table = OracleInheriteds

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ReactorInheritanceOracleRepository>(resource)
    }

    override val repository: ReactorInheritanceOracleRepository by lazy {
        getContextRepository()
    }
}

class ReactorInheritanceOracleRepository(sqlClient: ReactorSqlClient) :
    ReactorInheritanceRepository<OracleInheriteds>(sqlClient, OracleInheriteds)

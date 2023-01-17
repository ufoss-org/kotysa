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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongTest

@Order(2)
class JdbcSelectLongOracleTest : AbstractR2dbcOracleTest<ReactorLongRepositoryOracleSelect>(),
    ReactorSelectLongTest<OracleLongs, ReactorLongRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<ReactorLongRepositoryOracleSelect>(resource)
    }

    override val repository: ReactorLongRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class ReactorLongRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLongRepository<OracleLongs>(sqlClient, OracleLongs)

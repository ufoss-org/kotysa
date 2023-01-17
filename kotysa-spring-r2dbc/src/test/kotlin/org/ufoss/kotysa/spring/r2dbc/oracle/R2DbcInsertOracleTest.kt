/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.dao.DataIntegrityViolationException
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.test.OracleInts
import org.ufoss.kotysa.test.OracleLongs
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInsertTest

class R2DbcInsertOracleTest : AbstractR2dbcOracleTest<OracleInsertRepository>(),
    ReactorInsertTest<OracleInts, OracleLongs, OracleCustomers, OracleInsertRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OracleInsertRepository>(resource)
    }

    override val exceptionClass = DataIntegrityViolationException::class.java

    override val repository: OracleInsertRepository by lazy {
        getContextRepository()
    }
}

class OracleInsertRepository(sqlClient: ReactorSqlClient) :
    ReactorInsertRepository<OracleInts, OracleLongs, OracleCustomers>(
        sqlClient,
        OracleInts,
        OracleLongs,
        OracleCustomers
    )

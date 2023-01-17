/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleOffsetDateTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOffsetDateTimeTest

class JdbcSelectOffsetDateTimeOracleTest : AbstractR2dbcOracleTest<OffsetDateTimeRepositoryOracleSelect>(),
    ReactorSelectOffsetDateTimeTest<OracleOffsetDateTimes, OffsetDateTimeRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OffsetDateTimeRepositoryOracleSelect>(resource)
    }

    override val repository: OffsetDateTimeRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class OffsetDateTimeRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectOffsetDateTimeRepository<OracleOffsetDateTimes>(sqlClient, OracleOffsetDateTimes)

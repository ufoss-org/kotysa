/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeAsTimestampTest

class JdbcSelectLocalDateTimeAsTimestampOracleTest :
    AbstractR2dbcOracleTest<LocalDateTimeAsTimestampRepositoryOracleSelect>(),
    ReactorSelectLocalDateTimeAsTimestampTest<OracleLocalDateTimeAsTimestamps,
            LocalDateTimeAsTimestampRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LocalDateTimeAsTimestampRepositoryOracleSelect>(resource)
    }

    override val repository: LocalDateTimeAsTimestampRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateTimeAsTimestampRepository<OracleLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleLocalDateTimeAsTimestamps
    )

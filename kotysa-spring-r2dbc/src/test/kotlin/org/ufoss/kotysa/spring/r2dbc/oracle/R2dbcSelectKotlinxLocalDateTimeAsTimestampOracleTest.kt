/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeAsTimestampTest

class JdbcSelectKotlinxLocalDateTimeAsTimestampOracleTest :
    AbstractR2dbcOracleTest<KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect>(),
    ReactorSelectKotlinxLocalDateTimeAsTimestampTest<OracleKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect>(resource)
    }

    override val repository: KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateTimeAsTimestampRepository<OracleKotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleKotlinxLocalDateTimeAsTimestamps
    )

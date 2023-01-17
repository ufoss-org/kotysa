/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleKotlinxLocalDates
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTest

class JdbcSelectKotlinxLocalDateOracleTest :
    AbstractR2dbcOracleTest<KotlinxLocalDateRepositoryOracleSelect>(),
    ReactorSelectKotlinxLocalDateTest<OracleKotlinxLocalDates, KotlinxLocalDateRepositoryOracleSelect,
            ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateRepositoryOracleSelect>(resource)
    }

    override val repository: KotlinxLocalDateRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class KotlinxLocalDateRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateRepository<OracleKotlinxLocalDates>(sqlClient, OracleKotlinxLocalDates)

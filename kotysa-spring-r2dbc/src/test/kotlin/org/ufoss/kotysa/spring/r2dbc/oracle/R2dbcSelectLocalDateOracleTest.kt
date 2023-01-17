/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleLocalDates
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTest

class JdbcSelectLocalDateOracleTest : AbstractR2dbcOracleTest<LocalDateRepositoryOracleSelect>(),
    ReactorSelectLocalDateTest<OracleLocalDates, LocalDateRepositoryOracleSelect, ReactorTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LocalDateRepositoryOracleSelect>(resource)
    }

    override val repository: LocalDateRepositoryOracleSelect by lazy {
        getContextRepository()
    }
}

class LocalDateRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateRepository<OracleLocalDates>(sqlClient, OracleLocalDates)

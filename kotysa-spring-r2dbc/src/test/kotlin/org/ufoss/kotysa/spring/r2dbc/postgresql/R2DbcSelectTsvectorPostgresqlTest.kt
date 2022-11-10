/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectTsvectorRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectTsvectorTest

class R2DbcSelectTsvectorPostgresqlTest : AbstractR2dbcPostgresqlTest<TsvectorPostgresqlRepository>(),
    ReactorSelectTsvectorTest<TsvectorPostgresqlRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<TsvectorPostgresqlRepository>(resource)
    }

    override val repository: TsvectorPostgresqlRepository by lazy {
        getContextRepository()
    }
}

class TsvectorPostgresqlRepository(client: DatabaseClient) :
    ReactorSelectTsvectorRepository(client.sqlClient(postgresqlTables))

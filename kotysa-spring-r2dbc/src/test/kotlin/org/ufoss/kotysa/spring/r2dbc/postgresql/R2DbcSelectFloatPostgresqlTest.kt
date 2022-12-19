/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlFloats
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatTest

class R2DbcSelectFloatPostgresqlTest : AbstractR2dbcPostgresqlTest<FloatPostgresqlRepository>(),
    ReactorSelectFloatTest<PostgresqlFloats, FloatPostgresqlRepository,
            ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<FloatPostgresqlRepository>(resource)
    }

    override val repository: FloatPostgresqlRepository by lazy {
        getContextRepository()
    }
}

class FloatPostgresqlRepository(client: DatabaseClient) :
    ReactorSelectFloatRepository<PostgresqlFloats>(
        client.sqlClient(postgresqlTables),
        PostgresqlFloats
    )

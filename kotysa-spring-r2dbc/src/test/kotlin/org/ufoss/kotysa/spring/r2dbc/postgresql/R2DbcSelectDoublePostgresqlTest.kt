/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlDoubles
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleTest

class R2DbcSelectDoublePostgresqlTest : AbstractR2dbcPostgresqlTest<DoublePostgresqlRepository>(),
    ReactorSelectDoubleTest<PostgresqlDoubles, DoublePostgresqlRepository,
            ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<DoublePostgresqlRepository>(resource)
    }

    override val repository: DoublePostgresqlRepository by lazy {
        getContextRepository()
    }
}

class DoublePostgresqlRepository(client: DatabaseClient) :
    ReactorSelectDoubleRepository<PostgresqlDoubles>(
        client.sqlClient(postgresqlTables),
        PostgresqlDoubles
    )

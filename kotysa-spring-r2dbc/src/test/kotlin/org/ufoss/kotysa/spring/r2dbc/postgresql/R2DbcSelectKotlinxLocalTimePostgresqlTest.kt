/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeTest

class R2DbcSelectKotlinxLocalTimePostgresqlTest : AbstractR2dbcPostgresqlTest<KotlinxLocalTimePostgresqlRepository>(),
    ReactorSelectKotlinxLocalTimeTest<PostgresqlKotlinxLocalTimes, KotlinxLocalTimePostgresqlRepository,
            ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalTimePostgresqlRepository>(resource)
    }

    override val repository: KotlinxLocalTimePostgresqlRepository by lazy {
        getContextRepository()
    }
}

class KotlinxLocalTimePostgresqlRepository(client: DatabaseClient) :
    ReactorSelectKotlinxLocalTimeRepository<PostgresqlKotlinxLocalTimes>(
        client.sqlClient(postgresqlTables),
        PostgresqlKotlinxLocalTimes
    )

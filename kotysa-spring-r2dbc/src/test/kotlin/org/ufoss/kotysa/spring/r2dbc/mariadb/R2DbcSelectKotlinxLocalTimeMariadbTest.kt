/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalTimeTest

class R2DbcSelectKotlinxLocalTimeMariadbTest : AbstractR2dbcMariadbTest<KotlinxLocalTimeMariadbRepository>(),
    ReactorSelectKotlinxLocalTimeTest<MariadbKotlinxLocalTimes, KotlinxLocalTimeMariadbRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalTimeMariadbRepository>(resource)
    }

    override val repository: KotlinxLocalTimeMariadbRepository by lazy {
        getContextRepository()
    }
}

class KotlinxLocalTimeMariadbRepository(client: DatabaseClient) :
    ReactorSelectKotlinxLocalTimeRepository<MariadbKotlinxLocalTimes>(
        client.sqlClient(mariadbTables),
        MariadbKotlinxLocalTimes
    )

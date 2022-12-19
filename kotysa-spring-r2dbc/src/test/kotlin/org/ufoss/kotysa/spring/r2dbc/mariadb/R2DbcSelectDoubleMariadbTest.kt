/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbDoubles
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleTest

class R2DbcSelectDoubleMariadbTest : AbstractR2dbcMariadbTest<DoubleMariadbRepository>(),
    ReactorSelectDoubleTest<MariadbDoubles, DoubleMariadbRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<DoubleMariadbRepository>(resource)
    }

    override val repository: DoubleMariadbRepository by lazy {
        getContextRepository()
    }
}

class DoubleMariadbRepository(client: DatabaseClient) :
    ReactorSelectDoubleRepository<MariadbDoubles>(
        client.sqlClient(mariadbTables),
        MariadbDoubles
    )

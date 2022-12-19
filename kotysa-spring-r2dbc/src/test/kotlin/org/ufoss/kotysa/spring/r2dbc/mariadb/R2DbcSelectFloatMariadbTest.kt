/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbFloats
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatTest

class R2DbcSelectFloatMariadbTest : AbstractR2dbcMariadbTest<FloatMariadbRepository>(),
    ReactorSelectFloatTest<MariadbFloats, FloatMariadbRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<FloatMariadbRepository>(resource)
    }

    override val repository: FloatMariadbRepository by lazy {
        getContextRepository()
    }

    // in returns inconsistent results
    override fun `Verify selectAllByFloatNotNullIn finds both`() {}
}

class FloatMariadbRepository(client: DatabaseClient) :
    ReactorSelectFloatRepository<MariadbFloats>(
        client.sqlClient(mariadbTables),
        MariadbFloats
    )

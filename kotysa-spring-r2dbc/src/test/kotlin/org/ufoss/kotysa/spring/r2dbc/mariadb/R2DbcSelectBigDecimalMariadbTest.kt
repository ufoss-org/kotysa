/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbBigDecimals
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalTest

class R2DbcSelectBigDecimalMariadbTest : AbstractR2dbcMariadbTest<BigDecimalMariadbRepository>(),
    ReactorSelectBigDecimalTest<MariadbBigDecimals, BigDecimalMariadbRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<BigDecimalMariadbRepository>(resource)
    }

    override val repository: BigDecimalMariadbRepository by lazy {
        getContextRepository()
    }
}

class BigDecimalMariadbRepository(client: DatabaseClient) :
    ReactorSelectBigDecimalRepository<MariadbBigDecimals>(
        client.sqlClient(mariadbTables),
        MariadbBigDecimals
    )

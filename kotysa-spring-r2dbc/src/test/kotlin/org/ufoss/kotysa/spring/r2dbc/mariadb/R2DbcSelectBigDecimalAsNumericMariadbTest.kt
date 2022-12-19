/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbBigDecimalAsNumerics
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericTest

class R2DbcSelectBigDecimalAsNumericMariadbTest : AbstractR2dbcMariadbTest<BigDecimalAsNumericMariadbRepository>(),
    ReactorSelectBigDecimalAsNumericTest<MariadbBigDecimalAsNumerics, BigDecimalAsNumericMariadbRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<BigDecimalAsNumericMariadbRepository>(resource)
    }

    override val repository: BigDecimalAsNumericMariadbRepository by lazy {
        getContextRepository()
    }
}

class BigDecimalAsNumericMariadbRepository(client: DatabaseClient) :
    ReactorSelectBigDecimalAsNumericRepository<MariadbBigDecimalAsNumerics>(
        client.sqlClient(mariadbTables),
        MariadbBigDecimalAsNumerics
    )

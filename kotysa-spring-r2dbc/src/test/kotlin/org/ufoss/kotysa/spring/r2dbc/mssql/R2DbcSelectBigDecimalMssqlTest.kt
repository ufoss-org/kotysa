/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlBigDecimals
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalTest

class R2DbcSelectBigDecimalMssqlTest :
    AbstractR2dbcMssqlTest<SelectBigDecimalMssqlRepository>(),
    ReactorSelectBigDecimalTest<MssqlBigDecimals, SelectBigDecimalMssqlRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<SelectBigDecimalMssqlRepository>(resource)
    }

    override val repository: SelectBigDecimalMssqlRepository by lazy {
        getContextRepository()
    }
}


class SelectBigDecimalMssqlRepository(client: DatabaseClient) :
    ReactorSelectBigDecimalRepository<MssqlBigDecimals>(client.sqlClient(mssqlTables), MssqlBigDecimals)

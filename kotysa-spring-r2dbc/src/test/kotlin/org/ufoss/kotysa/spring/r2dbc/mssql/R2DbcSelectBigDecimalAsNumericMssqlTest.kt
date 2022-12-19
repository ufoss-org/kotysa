/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericTest

class R2DbcSelectBigDecimalAsNumericMssqlTest :
    AbstractR2dbcMssqlTest<SelectBigDecimalAsNumericMssqlRepository>(),
    ReactorSelectBigDecimalAsNumericTest<MssqlBigDecimalAsNumerics, SelectBigDecimalAsNumericMssqlRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<SelectBigDecimalAsNumericMssqlRepository>(resource)
    }

    override val repository: SelectBigDecimalAsNumericMssqlRepository by lazy {
        getContextRepository()
    }
}


class SelectBigDecimalAsNumericMssqlRepository(client: DatabaseClient) :
    ReactorSelectBigDecimalAsNumericRepository<MssqlBigDecimalAsNumerics>(client.sqlClient(mssqlTables), MssqlBigDecimalAsNumerics)

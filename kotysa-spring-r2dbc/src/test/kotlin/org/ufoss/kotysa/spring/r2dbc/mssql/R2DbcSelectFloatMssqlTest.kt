/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlFloats
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatTest

class R2DbcSelectFloatMssqlTest :
    AbstractR2dbcMssqlTest<SelectFloatMssqlRepository>(),
    ReactorSelectFloatTest<MssqlFloats, SelectFloatMssqlRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<SelectFloatMssqlRepository>(resource)
    }

    override val repository: SelectFloatMssqlRepository by lazy {
        getContextRepository()
    }
}


class SelectFloatMssqlRepository(client: DatabaseClient) :
    ReactorSelectFloatRepository<MssqlFloats>(client.sqlClient(mssqlTables), MssqlFloats)

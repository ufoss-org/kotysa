/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlDoubles
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleTest

class R2DbcSelectDoubleMssqlTest :
    AbstractR2dbcMssqlTest<SelectDoubleMssqlRepository>(),
    ReactorSelectDoubleTest<MssqlDoubles, SelectDoubleMssqlRepository, ReactorTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<SelectDoubleMssqlRepository>(resource)
    }

    override val repository: SelectDoubleMssqlRepository by lazy {
        getContextRepository()
    }
}


class SelectDoubleMssqlRepository(client: DatabaseClient) :
    ReactorSelectDoubleRepository<MssqlDoubles>(client.sqlClient(mssqlTables), MssqlDoubles)

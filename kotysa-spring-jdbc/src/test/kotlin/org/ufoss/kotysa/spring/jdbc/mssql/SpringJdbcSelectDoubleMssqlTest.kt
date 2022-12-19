/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlDoubles
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class SpringJdbcSelectDoubleMssqlTest : AbstractSpringJdbcMssqlTest<DoubleRepositoryMssqlSelect>(),
    SelectDoubleTest<MssqlDoubles, DoubleRepositoryMssqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<DoubleRepositoryMssqlSelect>(resource)
    }

    override val repository: DoubleRepositoryMssqlSelect by lazy {
        getContextRepository()
    }
}

class DoubleRepositoryMssqlSelect(client: JdbcOperations) :
    SelectDoubleRepository<MssqlDoubles>(client.sqlClient(mssqlTables), MssqlDoubles)

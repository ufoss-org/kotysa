/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class SpringJdbcSelectMinMaxAvgSumMssqlTest : AbstractSpringJdbcMssqlTest<MinMaxAvgSumRepositoryMssqlSelect>(),
    SelectMinMaxAvgSumTest<MssqlCustomers, MinMaxAvgSumRepositoryMssqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<MinMaxAvgSumRepositoryMssqlSelect>(resource)
    }

    override val repository: MinMaxAvgSumRepositoryMssqlSelect by lazy {
        getContextRepository()
    }
}

class MinMaxAvgSumRepositoryMssqlSelect(client: JdbcOperations) :
    SelectMinMaxAvgSumRepository<MssqlCustomers>(client.sqlClient(mssqlTables), MssqlCustomers)

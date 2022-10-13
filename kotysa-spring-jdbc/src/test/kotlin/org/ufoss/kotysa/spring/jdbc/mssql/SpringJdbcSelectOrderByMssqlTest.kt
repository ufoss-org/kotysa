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
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class SpringJdbcSelectOrderByMssqlTest : AbstractSpringJdbcMssqlTest<OrderByRepositoryMssqlSelect>(),
    SelectOrderByTest<MssqlCustomers, OrderByRepositoryMssqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OrderByRepositoryMssqlSelect>(resource)
    }

    override val repository: OrderByRepositoryMssqlSelect by lazy {
        getContextRepository()
    }
}

class OrderByRepositoryMssqlSelect(client: JdbcOperations) :
    SelectOrderByRepository<MssqlCustomers>(client.sqlClient(mssqlTables), MssqlCustomers)

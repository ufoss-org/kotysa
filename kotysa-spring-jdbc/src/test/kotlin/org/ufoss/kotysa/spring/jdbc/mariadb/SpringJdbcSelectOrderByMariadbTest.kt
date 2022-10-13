/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class SpringJdbcSelectOrderByMariadbTest : AbstractSpringJdbcMariadbTest<OrderByRepositoryMariadbSelect>(),
    SelectOrderByTest<MariadbCustomers, OrderByRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OrderByRepositoryMariadbSelect>(resource)
    }

    override val repository: OrderByRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class OrderByRepositoryMariadbSelect(client: JdbcOperations) :
    SelectOrderByRepository<MariadbCustomers>(client.sqlClient(mariadbTables), MariadbCustomers)

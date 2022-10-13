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
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class SpringJdbcSelectMinMaxAvgSumMariadbTest : AbstractSpringJdbcMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>(),
    SelectMinMaxAvgSumTest<MariadbCustomers, MinMaxAvgSumRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<MinMaxAvgSumRepositoryMariadbSelect>(resource)
    }

    override val repository: MinMaxAvgSumRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class MinMaxAvgSumRepositoryMariadbSelect(client: JdbcOperations) :
    SelectMinMaxAvgSumRepository<MariadbCustomers>(client.sqlClient(mariadbTables), MariadbCustomers)

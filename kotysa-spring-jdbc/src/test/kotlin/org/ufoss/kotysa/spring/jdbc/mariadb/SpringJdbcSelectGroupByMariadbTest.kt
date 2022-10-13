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
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class SpringJdbcSelectGroupByMariadbTest : AbstractSpringJdbcMariadbTest<GroupByRepositoryMariadbSelect>(),
    SelectGroupByTest<MariadbCustomers, GroupByRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<GroupByRepositoryMariadbSelect>(resource)
    }

    override val repository: GroupByRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class GroupByRepositoryMariadbSelect(client: JdbcOperations) :
    SelectGroupByRepository<MariadbCustomers>(client.sqlClient(mariadbTables), MariadbCustomers)

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
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class SpringJdbcSelectGroupByMssqlTest : AbstractSpringJdbcMssqlTest<GroupByRepositoryMssqlSelect>(),
    SelectGroupByTest<MssqlCustomers, GroupByRepositoryMssqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<GroupByRepositoryMssqlSelect>(resource)
    }

    override val repository: GroupByRepositoryMssqlSelect by lazy {
        getContextRepository()
    }
}

class GroupByRepositoryMssqlSelect(client: JdbcOperations) :
    SelectGroupByRepository<MssqlCustomers>(client.sqlClient(mssqlTables), MssqlCustomers)

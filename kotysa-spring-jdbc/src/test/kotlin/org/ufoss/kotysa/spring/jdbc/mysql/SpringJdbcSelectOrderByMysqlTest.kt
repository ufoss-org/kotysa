/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class SpringJdbcSelectOrderByMysqlTest : AbstractSpringJdbcMysqlTest<OrderByRepositoryMysqlSelect>(),
    SelectOrderByTest<MysqlCustomers, OrderByRepositoryMysqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OrderByRepositoryMysqlSelect>(resource)
    }

    override val repository: OrderByRepositoryMysqlSelect by lazy {
        getContextRepository()
    }
}

class OrderByRepositoryMysqlSelect(client: JdbcOperations) :
    SelectOrderByRepository<MysqlCustomers>(client.sqlClient(mysqlTables), MysqlCustomers)

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
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class SpringJdbcSelectMinMaxAvgSumMysqlTest : AbstractSpringJdbcMysqlTest<MinMaxAvgSumRepositoryMysqlSelect>(),
    SelectMinMaxAvgSumTest<MysqlCustomers, MinMaxAvgSumRepositoryMysqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<MinMaxAvgSumRepositoryMysqlSelect>(resource)
    }

    override val repository: MinMaxAvgSumRepositoryMysqlSelect by lazy {
        getContextRepository()
    }
}

class MinMaxAvgSumRepositoryMysqlSelect(client: JdbcOperations) :
    SelectMinMaxAvgSumRepository<MysqlCustomers>(client.sqlClient(mysqlTables), MysqlCustomers)

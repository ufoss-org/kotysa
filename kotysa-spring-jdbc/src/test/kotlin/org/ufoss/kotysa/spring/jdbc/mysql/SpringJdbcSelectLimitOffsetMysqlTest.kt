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
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class SpringJdbcSelectLimitOffsetMysqlTest : AbstractSpringJdbcMysqlTest<LimitOffsetRepositoryMysqlSelect>(),
    SelectLimitOffsetTest<MysqlCustomers, LimitOffsetRepositoryMysqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LimitOffsetRepositoryMysqlSelect>(resource)
    }

    override val repository: LimitOffsetRepositoryMysqlSelect by lazy {
        getContextRepository()
    }
}

class LimitOffsetRepositoryMysqlSelect(client: JdbcOperations) :
    SelectLimitOffsetRepository<MysqlCustomers>(client.sqlClient(mysqlTables), MysqlCustomers)

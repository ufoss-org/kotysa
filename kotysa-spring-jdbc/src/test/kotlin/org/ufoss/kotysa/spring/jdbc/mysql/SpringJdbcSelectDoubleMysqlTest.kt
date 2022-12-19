/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlDoubles
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class SpringJdbcSelectDoubleMysqlTest : AbstractSpringJdbcMysqlTest<DoubleRepositoryMysqlSelect>(),
    SelectDoubleTest<MysqlDoubles, DoubleRepositoryMysqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<DoubleRepositoryMysqlSelect>(resource)
    }

    override val repository: DoubleRepositoryMysqlSelect by lazy {
        getContextRepository()
    }
}

class DoubleRepositoryMysqlSelect(client: JdbcOperations) :
    SelectDoubleRepository<MysqlDoubles>(client.sqlClient(mysqlTables), MysqlDoubles)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlLocalTimes
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalTimeTest

class SpringJdbcSelectLocalTimeMysqlTest : AbstractSpringJdbcMysqlTest<LocalTimeRepositoryMysqlSelect>(),
    SelectLocalTimeTest<MysqlLocalTimes, LocalTimeRepositoryMysqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LocalTimeRepositoryMysqlSelect>(resource)
    }

    override val repository: LocalTimeRepositoryMysqlSelect by lazy {
        getContextRepository()
    }
}

class LocalTimeRepositoryMysqlSelect(client: JdbcOperations) :
    SelectLocalTimeRepository<MysqlLocalTimes>(client.sqlClient(mysqlTables), MysqlLocalTimes)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlInheriteds
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class SpringJdbcInheritanceMysqlTest : AbstractSpringJdbcMysqlTest<InheritanceMysqlRepository>(),
    InheritanceTest<MysqlInheriteds, InheritanceMysqlRepository, SpringJdbcTransaction> {
    override val table = MysqlInheriteds

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<InheritanceMysqlRepository>(resource)
    }

    override val repository: InheritanceMysqlRepository by lazy {
        getContextRepository()
    }
}

class InheritanceMysqlRepository(client: JdbcOperations) :
    InheritanceRepository<MysqlInheriteds>(client.sqlClient(mysqlTables), MysqlInheriteds)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlInheriteds
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class SpringJdbcInheritanceMssqlTest : AbstractSpringJdbcMssqlTest<InheritanceMssqlRepository>(),
    InheritanceTest<MssqlInheriteds, InheritanceMssqlRepository, SpringJdbcTransaction> {
    override val table = MssqlInheriteds

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<InheritanceMssqlRepository>(resource)
    }

    override val repository: InheritanceMssqlRepository by lazy {
        getContextRepository()
    }
}

class InheritanceMssqlRepository(client: JdbcOperations) :
    InheritanceRepository<MssqlInheriteds>(client.sqlClient(mssqlTables), MssqlInheriteds)

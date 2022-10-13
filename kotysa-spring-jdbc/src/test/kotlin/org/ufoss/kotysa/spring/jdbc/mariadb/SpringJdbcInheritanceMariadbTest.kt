/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbInheriteds
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class SpringJdbcInheritanceMariadbTest : AbstractSpringJdbcMariadbTest<InheritanceMariadbRepository>(),
    InheritanceTest<MariadbInheriteds, InheritanceMariadbRepository, SpringJdbcTransaction> {
    override val table = MariadbInheriteds

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<InheritanceMariadbRepository>(resource)
    }

    override val repository: InheritanceMariadbRepository by lazy {
        getContextRepository()
    }
}

class InheritanceMariadbRepository(client: JdbcOperations) :
    InheritanceRepository<MariadbInheriteds>(client.sqlClient(mariadbTables), MariadbInheriteds)

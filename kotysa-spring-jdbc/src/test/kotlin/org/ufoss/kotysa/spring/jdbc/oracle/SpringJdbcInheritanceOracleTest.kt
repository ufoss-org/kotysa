/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.OracleInheriteds
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.oracleTables
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class SpringJdbcInheritanceOracleTest : AbstractSpringJdbcOracleTest<InheritanceOracleRepository>(),
    InheritanceTest<OracleInheriteds, InheritanceOracleRepository, SpringJdbcTransaction> {
    override val table = OracleInheriteds

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<InheritanceOracleRepository>(resource)
    }

    override val repository: InheritanceOracleRepository by lazy {
        getContextRepository()
    }
}

class InheritanceOracleRepository(client: JdbcOperations) :
    InheritanceRepository<OracleInheriteds>(client.sqlClient(oracleTables), OracleInheriteds)

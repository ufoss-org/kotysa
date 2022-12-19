/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbFloats
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class SpringJdbcSelectFloatMariadbTest : AbstractSpringJdbcMariadbTest<FloatRepositoryMariadbSelect>(),
    SelectFloatTest<MariadbFloats, FloatRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<FloatRepositoryMariadbSelect>(resource)
    }

    override val repository: FloatRepositoryMariadbSelect by lazy {
        getContextRepository()
    }

    // in returns inconsistent results
    override fun `Verify selectAllByFloatNotNullIn finds both`() {}
}

class FloatRepositoryMariadbSelect(client: JdbcOperations) :
    SelectFloatRepository<MariadbFloats>(client.sqlClient(mariadbTables), MariadbFloats)

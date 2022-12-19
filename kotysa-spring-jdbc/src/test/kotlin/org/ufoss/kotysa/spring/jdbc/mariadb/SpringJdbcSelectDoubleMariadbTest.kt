/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbDoubles
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class SpringJdbcSelectDoubleMariadbTest : AbstractSpringJdbcMariadbTest<DoubleRepositoryMariadbSelect>(),
    SelectDoubleTest<MariadbDoubles, DoubleRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<DoubleRepositoryMariadbSelect>(resource)
    }

    override val repository: DoubleRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class DoubleRepositoryMariadbSelect(client: JdbcOperations) :
    SelectDoubleRepository<MariadbDoubles>(client.sqlClient(mariadbTables), MariadbDoubles)

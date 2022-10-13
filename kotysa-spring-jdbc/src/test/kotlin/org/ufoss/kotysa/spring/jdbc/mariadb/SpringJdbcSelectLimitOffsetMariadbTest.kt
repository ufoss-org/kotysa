/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class SpringJdbcSelectLimitOffsetMariadbTest : AbstractSpringJdbcMariadbTest<LimitOffsetRepositoryMariadbSelect>(),
    SelectLimitOffsetTest<MariadbCustomers, LimitOffsetRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LimitOffsetRepositoryMariadbSelect>(resource)
    }

    override val repository: LimitOffsetRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}

class LimitOffsetRepositoryMariadbSelect(client: JdbcOperations) :
    SelectLimitOffsetRepository<MariadbCustomers>(client.sqlClient(mariadbTables), MariadbCustomers)

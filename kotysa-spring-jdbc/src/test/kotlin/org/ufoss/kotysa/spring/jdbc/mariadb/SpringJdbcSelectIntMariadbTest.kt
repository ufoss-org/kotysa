/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.blocking.SelectIntRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntTest

@Order(1)
class SpringJdbcSelectIntMariadbTest : AbstractSpringJdbcMariadbTest<SelectIntRepositoryMariadbSelect>(),
    SelectIntTest<MariadbInts, SelectIntRepositoryMariadbSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<SelectIntRepositoryMariadbSelect>(resource)
    }

    override val repository: SelectIntRepositoryMariadbSelect by lazy {
        getContextRepository()
    }
}


class SelectIntRepositoryMariadbSelect(client: JdbcOperations) :
    SelectIntRepository<MariadbInts>(client.sqlClient(mariadbTables), MariadbInts)

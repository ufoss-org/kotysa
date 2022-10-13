/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest

@Order(3)
class SpringJdbcInsertMariadbTest : AbstractSpringJdbcMariadbTest<RepositoryMariadbInsert>(),
    InsertTest<MariadbInts, MariadbLongs, MariadbCustomers, RepositoryMariadbInsert, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<RepositoryMariadbInsert>(resource)
    }

    override val repository: RepositoryMariadbInsert by lazy {
        getContextRepository()
    }

    override val exceptionClass = DataIntegrityViolationException::class.java
}

class RepositoryMariadbInsert(dbClient: JdbcOperations) :
    InsertRepository<MariadbInts, MariadbLongs, MariadbCustomers>(
        dbClient.sqlClient(mariadbTables),
        MariadbInts,
        MariadbLongs,
        MariadbCustomers
    )

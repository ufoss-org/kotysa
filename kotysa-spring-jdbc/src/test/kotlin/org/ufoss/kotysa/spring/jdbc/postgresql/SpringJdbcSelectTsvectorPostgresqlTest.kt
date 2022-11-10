/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectTsvectorRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectTsvectorTest

class SpringJdbcSelectTsvectorPostgresqlTest : AbstractSpringJdbcPostgresqlTest<TsvectorRepositoryPostgresqlSelect>(),
    SelectTsvectorTest<TsvectorRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<TsvectorRepositoryPostgresqlSelect>(resource)
    }

    override val repository: TsvectorRepositoryPostgresqlSelect by lazy {
        getContextRepository()
    }
}

class TsvectorRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectTsvectorRepository(client.sqlClient(postgresqlTables))

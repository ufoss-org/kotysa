/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlUuids
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectUuidRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectUuidTest

class SpringJdbcSelectUuidPostgresqlTest : AbstractSpringJdbcPostgresqlTest<UuidRepositoryPostgresqlSelect>(),
    SelectUuidTest<PostgresqlUuids, UuidRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UuidRepositoryPostgresqlSelect>(resource)
    }

    override val repository: UuidRepositoryPostgresqlSelect by lazy {
        getContextRepository()
    }
}

class UuidRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectUuidRepository<PostgresqlUuids>(client.sqlClient(postgresqlTables), PostgresqlUuids)

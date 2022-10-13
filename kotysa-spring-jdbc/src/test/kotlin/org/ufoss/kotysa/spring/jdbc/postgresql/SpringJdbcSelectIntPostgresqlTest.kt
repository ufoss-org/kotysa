/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

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
class SpringJdbcSelectIntPostgresqlTest : AbstractSpringJdbcPostgresqlTest<SelectIntRepositoryPostgresqlSelect>(),
    SelectIntTest<PostgresqlInts, SelectIntRepositoryPostgresqlSelect, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<SelectIntRepositoryPostgresqlSelect>(resource)
    }

    override val repository: SelectIntRepositoryPostgresqlSelect by lazy {
        getContextRepository()
    }
}


class SelectIntRepositoryPostgresqlSelect(client: JdbcOperations) :
    SelectIntRepository<PostgresqlInts>(client.sqlClient(postgresqlTables), PostgresqlInts)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

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
class SpringJdbcInsertPostgresqlTest : AbstractSpringJdbcPostgresqlTest<RepositoryPostgresqlInsert>(),
    InsertTest<PostgresqlInts, PostgresqlLongs, PostgresqlCustomers, RepositoryPostgresqlInsert, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<RepositoryPostgresqlInsert>(resource)
    }

    override val repository: RepositoryPostgresqlInsert by lazy {
        getContextRepository()
    }

    override val exceptionClass = DataIntegrityViolationException::class.java
}

class RepositoryPostgresqlInsert(dbClient: JdbcOperations) :
    InsertRepository<PostgresqlInts, PostgresqlLongs, PostgresqlCustomers>(
        dbClient.sqlClient(postgresqlTables),
        PostgresqlInts,
        PostgresqlLongs,
        PostgresqlCustomers
    )

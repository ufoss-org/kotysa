/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class SpringJdbcSelectAndPostgresqlTest : AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlSelectAnd>(),
    SelectAndTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositorySpringJdbcPostgresqlSelectAnd,
            SpringJdbcTransaction> {
    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcPostgresqlSelectAnd>(resource)
    }

    override val repository: UserRepositorySpringJdbcPostgresqlSelectAnd by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcPostgresqlSelectAnd(client: JdbcOperations) :
    SelectAndRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        client.sqlClient(postgresqlTables),
        PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles
    )

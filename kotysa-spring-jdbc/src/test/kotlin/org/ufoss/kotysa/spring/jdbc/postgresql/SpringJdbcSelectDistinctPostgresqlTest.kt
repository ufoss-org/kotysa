/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.PostgresqlUserRoles
import org.ufoss.kotysa.test.PostgresqlUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctTest

class SpringJdbcSelectDistinctPostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlSelectDistinct>(),
    SelectDistinctTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositorySpringJdbcPostgresqlSelectDistinct,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcPostgresqlSelectDistinct>(resource)
    }

    override val repository: UserRepositorySpringJdbcPostgresqlSelectDistinct by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcPostgresqlSelectDistinct(client: JdbcOperations) :
    SelectDistinctRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        client.sqlClient(postgresqlTables),
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )

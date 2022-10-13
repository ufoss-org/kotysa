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
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteTest

class SpringJdbcUpdateDeletePostgresqlTest :
    AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlUpdateDelete>(),
    UpdateDeleteTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositorySpringJdbcPostgresqlUpdateDelete,
            SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcPostgresqlUpdateDelete>(resource)
    }

    override val repository: UserRepositorySpringJdbcPostgresqlUpdateDelete by lazy {
        getContextRepository()
    }
}

class UserRepositorySpringJdbcPostgresqlUpdateDelete(client: JdbcOperations) :
    UpdateDeleteRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        client.sqlClient(postgresqlTables),
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )

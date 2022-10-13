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
import org.ufoss.kotysa.test.repositories.blocking.SubQueryRepository
import org.ufoss.kotysa.test.repositories.blocking.SubQueryTest

class SpringJdbcSubQueryPostgresqlTest : AbstractSpringJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlSubQuery>(),
    SubQueryTest<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles, UserRepositoryJdbcPostgresqlSubQuery, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryJdbcPostgresqlSubQuery>(resource)
    }

    override val repository: UserRepositoryJdbcPostgresqlSubQuery by lazy {
        getContextRepository()
    }
}

class UserRepositoryJdbcPostgresqlSubQuery(client: JdbcOperations) :
    SubQueryRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        client.sqlClient(postgresqlTables),
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )

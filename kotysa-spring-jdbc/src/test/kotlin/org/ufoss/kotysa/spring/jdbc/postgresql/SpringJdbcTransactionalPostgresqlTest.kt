/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.repositories.blocking.AbstractUserRepository

class SpringJdbcTransactionalPostgresqlTest
    : AbstractSpringJdbcPostgresqlTest<UserRepositoryJdbcPostgresqldbTransactional>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryJdbcPostgresqldbTransactional>(resource)
    }

    override val repository: UserRepositoryJdbcPostgresqldbTransactional by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss inside readonly transaction`() {
        val user = operator.transactional { transaction ->
            transaction.readOnly = true
            repository.selectFirstByFirstname("John")
        }
        assertThat(user)
            .isEqualTo(userJdoe)
    }
}

class UserRepositoryJdbcPostgresqldbTransactional(client: JdbcOperations) :
    AbstractUserRepository<PostgresqlRoles, PostgresqlUsers, PostgresqlUserRoles>(
        client.sqlClient(postgresqlTables),
        PostgresqlRoles,
        PostgresqlUsers,
        PostgresqlUserRoles
    )

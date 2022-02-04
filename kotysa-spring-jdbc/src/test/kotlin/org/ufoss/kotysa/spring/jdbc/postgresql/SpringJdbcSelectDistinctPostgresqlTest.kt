/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class SpringJdbcSelectDistinctPostgresqlTest :
        AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlSelectDistinct>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcPostgresqlSelectDistinct>(resource)
    }

    override val repository: UserRepositorySpringJdbcPostgresqlSelectDistinct by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositorySpringJdbcPostgresqlSelectDistinct(client: JdbcOperations) : AbstractUserRepositorySpringJdbcPostgresql(client) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct PostgresqlRoles.label
                    from PostgresqlRoles
                    ).fetchAll()
}

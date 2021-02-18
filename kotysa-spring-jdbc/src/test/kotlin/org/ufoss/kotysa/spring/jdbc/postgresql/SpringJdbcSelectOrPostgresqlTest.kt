/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.POSTGRESQL_ROLE
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod


class SpringJdbcSelectOrPostgresqlTest : AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlSelectOr>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcPostgresqlSelectOr>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() {
        assertThat(repository.selectRolesByLabels(roleAdmin.label, roleGod.label))
                .hasSize(2)
                .containsExactlyInAnyOrder(roleAdmin, roleGod)
    }
}


class UserRepositorySpringJdbcPostgresqlSelectOr(client: JdbcOperations) : AbstractUserRepositorySpringJdbcPostgresql(client) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom POSTGRESQL_ROLE
                    where POSTGRESQL_ROLE.label eq label1
                    or POSTGRESQL_ROLE.label eq label2
                    ).fetchAll()
}

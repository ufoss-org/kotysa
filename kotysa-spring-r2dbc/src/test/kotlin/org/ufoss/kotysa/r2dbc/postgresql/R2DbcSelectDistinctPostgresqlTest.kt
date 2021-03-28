/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.POSTGRESQL_ROLE
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser


class R2DbcSelectDistinctPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryPostgresqlSelectDistinct>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryPostgresqlSelectDistinct>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels().toIterable())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryPostgresqlSelectDistinct(sqlClient: ReactorSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct POSTGRESQL_ROLE.label
                    from POSTGRESQL_ROLE
                    ).fetchAll()
}

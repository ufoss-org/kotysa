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


class R2DbcSelectOrPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryPostgresqlSelectOr>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryPostgresqlSelectOr>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() {
        assertThat(repository.selectRolesByLabels(roleAdmin.label, roleGod.label).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(roleAdmin, roleGod)
    }
}


class UserRepositoryPostgresqlSelectOr(sqlClient: ReactorSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom POSTGRESQL_ROLE
                    where POSTGRESQL_ROLE.label eq label1
                    or POSTGRESQL_ROLE.label eq label2
                    ).fetchAll()
}

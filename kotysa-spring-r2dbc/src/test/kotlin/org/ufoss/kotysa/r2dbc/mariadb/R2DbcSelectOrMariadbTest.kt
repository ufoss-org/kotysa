/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.MARIADB_ROLE
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser


class R2DbcSelectOrMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryMariadbSelectOr>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryMariadbSelectOr>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryMariadbSelectOr(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMariadb(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom MARIADB_ROLE
                    where MARIADB_ROLE.label eq label1
                    or MARIADB_ROLE.label eq label2
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2_ROLE
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod


class R2DbcSelectOrH2Test : AbstractR2dbcH2Test<UserRepositoryH2SelectOr>() {

    @BeforeAll
    fun beforeAll() {
        context = startContext<UserRepositoryH2SelectOr>()
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() {
        assertThat(repository.selectRolesByLabels(roleAdmin.label, roleGod.label).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(roleAdmin, roleGod)
    }
}


class UserRepositoryH2SelectOr(
        sqlClient: ReactorSqlClient,
) : AbstractUserRepositoryH2(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom H2_ROLE
                    where H2_ROLE.label eq label1
                    or H2_ROLE.label eq label2
                    ).fetchAll()
}

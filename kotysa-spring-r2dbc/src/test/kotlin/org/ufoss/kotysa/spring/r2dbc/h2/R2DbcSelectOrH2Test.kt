/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser


class R2DbcSelectOrH2Test : AbstractR2dbcH2Test<UserRepositoryH2SelectOr>() {
    override val context = startContext<UserRepositoryH2SelectOr>()
    override val repository = getContextRepository<UserRepositoryH2SelectOr>()

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryH2SelectOr(sqlClient: ReactorSqlClient) : AbstractUserRepositoryH2(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom H2Roles
                    where H2Roles.label eq label1
                    or H2Roles.label eq label2
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2_ROLE
import org.ufoss.kotysa.test.roleUser


class R2DbcSelectAndH2Test : AbstractR2dbcH2Test<UserRepositoryH2SelectAnd>() {
    override val context = startContext<UserRepositoryH2SelectAnd>()
    override val repository = getContextRepository<UserRepositoryH2SelectAnd>()

    @Test
    fun `Verify selectRolesByLabels finds h2User`() {
        assertThat(repository.selectRolesByLabels("u", "r").toIterable())
                .hasSize(1)
                .containsExactly(roleUser)
    }
}


class UserRepositoryH2SelectAnd(
    sqlClient: ReactorSqlClient,
) : org.ufoss.kotysa.spring.r2dbc.h2.AbstractUserRepositoryH2(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom H2_ROLE
                    where H2_ROLE.label contains label1
                    and H2_ROLE.label contains label2
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2_ROLE
import org.ufoss.kotysa.test.roleGod


class R2DbcSelectAndH2Test : AbstractR2dbcH2Test<UserRepositoryH2SelectAnd>() {

    @BeforeAll
    fun beforeAll() {
        context = startContext<UserRepositoryH2SelectAnd>()
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectRolesByLabels finds h2God`() {
        assertThat(repository.selectRolesByLabels("d", "g").toIterable())
                .hasSize(1)
                .containsExactly(roleGod)
    }
}


class UserRepositoryH2SelectAnd(
        sqlClient: ReactorSqlClient,
) : AbstractUserRepositoryH2(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom H2_ROLE
                    where H2_ROLE.label contains label1
                    and H2_ROLE.label contains label2
                    ).fetchAll()
}

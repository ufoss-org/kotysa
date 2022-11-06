/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.roleUser


class R2dbcSelectAndH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2SelectAnd>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcH2SelectAnd(sqlClient)

    @Test
    fun `Verify selectRolesByLabels finds h2User`() = runTest {
        assertThat(repository.selectRolesByLabels("u", "r").toList())
            .hasSize(1)
            .containsExactly(roleUser)
    }
}


class UserRepositoryJdbcH2SelectAnd(private val sqlClient: R2dbcSqlClient) : AbstractUserRepositoryR2dbcH2(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
        (sqlClient selectFrom H2Roles
                where H2Roles.label contains label1
                and H2Roles.label contains label2
                ).fetchAll()
}

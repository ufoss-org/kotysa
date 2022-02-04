/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser

class R2dbcSelectOrH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2SelectOr>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcH2SelectOr(sqlClient)

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() = runTest {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryJdbcH2SelectOr(private val sqlClient: R2dbcSqlClient) : AbstractUserRepositoryR2dbcH2(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
        (sqlClient selectFrom H2Roles
                where H2Roles.label eq label1
                or H2Roles.label eq label2
                ).fetchAll()
}

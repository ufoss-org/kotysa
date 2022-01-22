/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.H2_ROLE
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser

class R2dbcSelectOrH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2SelectOr>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcH2SelectOr(connection)

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() = runTest {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryJdbcH2SelectOr(connection: Connection) : AbstractUserRepositoryR2dbcH2(connection) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom H2_ROLE
                    where H2_ROLE.label eq label1
                    or H2_ROLE.label eq label2
                    ).fetchAll()
}

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
import org.ufoss.kotysa.test.roleUser


class R2dbcSelectAndH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2SelectAnd>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcH2SelectAnd(connection)
    
    @Test
    fun `Verify selectRolesByLabels finds h2User`() = runTest {
        assertThat(repository.selectRolesByLabels("u", "r").toList())
                .hasSize(1)
                .containsExactly(roleUser)
    }
}


class UserRepositoryJdbcH2SelectAnd(connection: Connection) : AbstractUserRepositoryR2dbcH2(connection) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom H2_ROLE
                    where H2_ROLE.label contains label1
                    and H2_ROLE.label contains label2
                    ).fetchAll()
}

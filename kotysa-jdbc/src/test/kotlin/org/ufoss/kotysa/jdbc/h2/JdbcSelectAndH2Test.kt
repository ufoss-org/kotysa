/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.H2_ROLE
import org.ufoss.kotysa.test.roleUser
import java.sql.Connection


class JdbcSelectAndH2Test : AbstractJdbcH2Test<UserRepositoryJdbcH2SelectAnd>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcH2SelectAnd(connection)
    
    @Test
    fun `Verify selectRolesByLabels finds h2User`() {
        assertThat(repository.selectRolesByLabels("u", "r"))
                .hasSize(1)
                .containsExactly(roleUser)
    }
}


class UserRepositoryJdbcH2SelectAnd(connection: Connection) : AbstractUserRepositoryJdbcH2(connection) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom H2_ROLE
                    where H2_ROLE.label contains label1
                    and H2_ROLE.label contains label2
                    ).fetchAll()
}

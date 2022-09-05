/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.roleUser

class JdbcSelectAndH2Test : AbstractJdbcH2Test<UserRepositoryJdbcH2SelectAnd>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcH2SelectAnd(sqlClient)
    
    @Test
    fun `Verify selectRolesByLabels finds h2User`() {
        assertThat(repository.selectRolesByLabels("u", "r"))
                .hasSize(1)
                .containsExactly(roleUser)
    }
}


class UserRepositoryJdbcH2SelectAnd(private val sqlClient: JdbcSqlClient) : AbstractUserRepositoryJdbcH2(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom H2Roles
                    where H2Roles.label contains label1
                    and H2Roles.label contains label2
                    ).fetchAll()
}

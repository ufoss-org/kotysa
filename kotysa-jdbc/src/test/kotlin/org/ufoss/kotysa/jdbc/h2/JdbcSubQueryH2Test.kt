/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.userBboss

class JdbcSubQueryH2Test : AbstractJdbcH2Test<UserRepositoryJdbcH2SubQuery>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcH2SubQuery(sqlClient)

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id))
            .isEqualTo(Pair(userBboss.firstname, roleAdmin.label))
    }
}


class UserRepositoryJdbcH2SubQuery(private val sqlClient: JdbcSqlClient) : AbstractUserRepositoryJdbcH2(sqlClient) {

    fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
        (sqlClient select H2Users.firstname and {
            (this select H2Roles.label
                    from H2Roles
                    where H2Roles.id eq H2Users.roleId
                    and H2Roles.label eq roleAdmin.label)
        }
                from H2Users
                where H2Users.id eq userId)
            .fetchOne()
}

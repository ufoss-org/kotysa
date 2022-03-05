/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSubQueryMariadbTest : AbstractJdbcMariadbTest<UserRepositoryJdbcMariadbSubQuery>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMariadbSubQuery(sqlClient)

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id))
            .isEqualTo(Pair(userBboss.firstname, roleAdmin.label))
    }
}


class UserRepositoryJdbcMariadbSubQuery(private val sqlClient: JdbcSqlClient) : AbstractUserRepositoryJdbcMariadb(sqlClient) {

    fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
        (sqlClient select MariadbUsers.firstname and {
            (this select MariadbRoles.label
                    from MariadbRoles
                    where MariadbRoles.id eq MariadbUsers.roleId
                    and MariadbRoles.label eq roleAdmin.label)
        }
                from MariadbUsers
                where MariadbUsers.id eq userId)
            .fetchOne()
}

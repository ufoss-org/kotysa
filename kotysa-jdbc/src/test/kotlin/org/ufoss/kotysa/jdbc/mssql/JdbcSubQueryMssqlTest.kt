/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSubQueryMssqlTest : AbstractJdbcMssqlTest<UserRepositoryJdbcMssqlSubQuery>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMssqlSubQuery(sqlClient)

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id))
            .isEqualTo(Pair(userBboss.firstname, roleAdmin.label))
    }
}


class UserRepositoryJdbcMssqlSubQuery(private val sqlClient: JdbcSqlClient) : AbstractUserRepositoryJdbcMssql(sqlClient) {

    fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
        (sqlClient select MssqlUsers.firstname and {
            (this select MssqlRoles.label
                    from MssqlRoles
                    where MssqlRoles.id eq MssqlUsers.roleId
                    and MssqlRoles.label eq roleAdmin.label)
        }
                from MssqlUsers
                where MssqlUsers.id eq userId)
            .fetchOne()
}

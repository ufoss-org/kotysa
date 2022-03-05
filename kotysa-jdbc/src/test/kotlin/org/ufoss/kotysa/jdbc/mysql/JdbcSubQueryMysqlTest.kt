/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSubQueryMysqlTest : AbstractJdbcMysqlTest<UserRepositoryJdbcMysqlSubQuery>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMysqlSubQuery(sqlClient)

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id))
            .isEqualTo(Pair(userBboss.firstname, roleAdmin.label))
    }
}


class UserRepositoryJdbcMysqlSubQuery(private val sqlClient: JdbcSqlClient) : AbstractUserRepositoryJdbcMysql(sqlClient) {

    fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
        (sqlClient select MysqlUsers.firstname and {
            (this select MysqlRoles.label
                    from MysqlRoles
                    where MysqlRoles.id eq MysqlUsers.roleId
                    and MysqlRoles.label eq roleAdmin.label)
        }
                from MysqlUsers
                where MysqlUsers.id eq userId)
            .fetchOne()
}

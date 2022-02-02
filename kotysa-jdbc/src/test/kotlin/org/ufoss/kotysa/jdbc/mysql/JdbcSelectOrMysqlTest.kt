/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.MYSQL_ROLE
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser

class JdbcSelectOrMysqlTest : AbstractJdbcMysqlTest<UserRepositoryJdbcMysqlSelectOr>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMysqlSelectOr(sqlClient)

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label))
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryJdbcMysqlSelectOr(private val sqlClient: JdbcSqlClient) :
    AbstractUserRepositoryJdbcMysql(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom MYSQL_ROLE
                    where MYSQL_ROLE.label eq label1
                    or MYSQL_ROLE.label eq label2
                    ).fetchAll()
}

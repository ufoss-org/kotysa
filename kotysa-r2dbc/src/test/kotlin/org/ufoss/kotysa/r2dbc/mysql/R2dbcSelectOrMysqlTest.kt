/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MYSQL_ROLE
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser

class R2dbcSelectOrMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlSelectOr>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMysqlSelectOr(connection)

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() = runTest {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryJdbcMysqlSelectOr(connection: Connection) : AbstractUserRepositoryR2dbcMysql(connection) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom MYSQL_ROLE
                    where MYSQL_ROLE.label eq label1
                    or MYSQL_ROLE.label eq label2
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MARIADB_ROLE
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser
import java.sql.Connection


class JdbcSelectOrMariadbTest : AbstractJdbcMariadbTest<UserRepositoryJdbcMariadbSelectOr>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMariadbSelectOr(connection)

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label))
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryJdbcMariadbSelectOr(connection: Connection) : AbstractUserRepositoryJdbcMariadb(connection) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom MARIADB_ROLE
                    where MARIADB_ROLE.label eq label1
                    or MARIADB_ROLE.label eq label2
                    ).fetchAll()
}

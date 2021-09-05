/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.POSTGRESQL_ROLE
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser
import java.sql.Connection


class JdbcSelectOrPostgresqlTest : AbstractJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectOr>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcPostgresqlSelectOr(connection)

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label))
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryJdbcPostgresqlSelectOr(connection: Connection) : AbstractUserRepositoryJdbcPostgresql(connection) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom POSTGRESQL_ROLE
                    where POSTGRESQL_ROLE.label eq label1
                    or POSTGRESQL_ROLE.label eq label2
                    ).fetchAll()
}

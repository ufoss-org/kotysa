/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MARIADB_ROLE
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser

class R2dbcSelectOrMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectOr>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMariadbSelectOr(connection)

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() = runTest {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryJdbcMariadbSelectOr(connection: Connection) : AbstractUserRepositoryR2dbcMariadb(connection) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom MARIADB_ROLE
                    where MARIADB_ROLE.label eq label1
                    or MARIADB_ROLE.label eq label2
                    ).fetchAll()
}

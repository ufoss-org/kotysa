/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.POSTGRESQL_ROLE
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser

class R2dbcSelectOrPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectOr>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcPostgresqlSelectOr(connection)

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() = runTest {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryJdbcPostgresqlSelectOr(connection: Connection) : AbstractUserRepositoryR2dbcPostgresql(connection) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom POSTGRESQL_ROLE
                    where POSTGRESQL_ROLE.label eq label1
                    or POSTGRESQL_ROLE.label eq label2
                    ).fetchAll()
}

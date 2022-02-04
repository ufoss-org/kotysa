/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser

class R2dbcSelectOrMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectOr>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMariadbSelectOr(sqlClient)

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() = runTest {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryJdbcMariadbSelectOr(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcMariadb(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom MariadbRoles
                    where MariadbRoles.label eq label1
                    or MariadbRoles.label eq label2
                    ).fetchAll()
}

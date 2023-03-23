/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser


class R2DbcSelectOrMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryMariadbSelectOr>() {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        UserRepositoryMariadbSelectOr(sqlClient)

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryMariadbSelectOr(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMariadb(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
        (sqlClient selectFrom MariadbRoles
                where MariadbRoles.label eq label1
                or MariadbRoles.label eq label2
                ).fetchAll()
}

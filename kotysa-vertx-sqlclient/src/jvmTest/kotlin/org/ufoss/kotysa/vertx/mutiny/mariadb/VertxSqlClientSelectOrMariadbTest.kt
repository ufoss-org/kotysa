/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient


class VertxSqlClientSelectOrMariadbTest : AbstractVertxSqlClientMariadbTest<UserRepositoryMariadbSelectOr>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryMariadbSelectOr(sqlClient)

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryMariadbSelectOr(sqlClient: MutinyVertxSqlClient) : AbstractUserRepositoryMariadb(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
        (sqlClient selectFrom MariadbRoles
                where MariadbRoles.label eq label1
                or MariadbRoles.label eq label2
                ).fetchAll()
}

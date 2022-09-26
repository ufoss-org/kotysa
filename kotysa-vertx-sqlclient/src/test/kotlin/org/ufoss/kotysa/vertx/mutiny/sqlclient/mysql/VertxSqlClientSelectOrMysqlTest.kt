/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient


class VertxSqlClientSelectOrMysqlTest : AbstractVertxSqlClientMysqlTest<UserRepositoryMysqlSelectOr>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryMysqlSelectOr(sqlClient)

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryMysqlSelectOr(sqlClient: VertxSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
        (sqlClient selectFrom MysqlRoles
                where MysqlRoles.label eq label1
                or MysqlRoles.label eq label2
                ).fetchAll()
}

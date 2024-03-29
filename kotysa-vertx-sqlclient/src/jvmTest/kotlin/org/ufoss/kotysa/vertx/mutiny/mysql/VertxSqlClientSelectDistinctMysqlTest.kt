/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient


class VertxSqlClientSelectDistinctMysqlTest :
    AbstractVertxSqlClientMysqlTest<UserRepositoryMysqlSelectDistinct>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryMysqlSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels().await().indefinitely())
            .hasSize(3)
            .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryMysqlSelectDistinct(sqlClient: MutinyVertxSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun selectDistinctRoleLabels() =
        (sqlClient selectDistinct MysqlRoles.label
                from MysqlRoles
                ).fetchAll()
}

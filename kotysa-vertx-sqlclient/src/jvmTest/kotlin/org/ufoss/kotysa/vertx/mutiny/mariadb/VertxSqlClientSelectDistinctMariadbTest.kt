/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient


class VertxSqlClientSelectDistinctMariadbTest :
    AbstractVertxSqlClientMariadbTest<UserRepositoryMariadbSelectDistinct>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryMariadbSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels().await().indefinitely())
            .hasSize(3)
            .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryMariadbSelectDistinct(sqlClient: MutinyVertxSqlClient) : AbstractUserRepositoryMariadb(sqlClient) {

    fun selectDistinctRoleLabels() =
        (sqlClient selectDistinct MariadbRoles.label
                from MariadbRoles
                ).fetchAll()
}

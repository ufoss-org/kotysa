/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient


class VertxSqlClientSelectDistinctMssqlTest :
    AbstractVertxSqlClientMssqlTest<UserRepositoryMssqlSelectDistinct>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryMssqlSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels().await().indefinitely())
            .hasSize(3)
            .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryMssqlSelectDistinct(sqlClient: VertxSqlClient) : AbstractUserRepositoryMssql(sqlClient) {

    fun selectDistinctRoleLabels() =
        (sqlClient selectDistinct MssqlRoles.label
                from MssqlRoles
                ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser


class R2DbcSelectDistinctMssqlTest : AbstractR2dbcMssqlTest<UserRepositoryMssqlSelectDistinct>() {

    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        UserRepositoryMssqlSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels().toIterable())
            .hasSize(3)
            .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryMssqlSelectDistinct(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMssql(sqlClient) {

    fun selectDistinctRoleLabels() =
        (sqlClient selectDistinct MssqlRoles.label
                from MssqlRoles
                ).fetchAll()
}

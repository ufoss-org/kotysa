/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectDistinctMssqlTest : AbstractJdbcMssqlTest<UserRepositoryJdbcMssqlSelectDistinct>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMssqlSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcMssqlSelectDistinct(private val sqlClient: JdbcSqlClient) :
    AbstractUserRepositoryJdbcMssql(sqlClient) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MssqlRoles.label
                    from MssqlRoles
                    ).fetchAll()
}

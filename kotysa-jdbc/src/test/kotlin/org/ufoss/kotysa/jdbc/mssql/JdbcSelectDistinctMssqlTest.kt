/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection


class JdbcSelectDistinctMssqlTest : AbstractJdbcMssqlTest<UserRepositoryJdbcMssqlSelectDistinct>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMssqlSelectDistinct(connection)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcMssqlSelectDistinct(connection: Connection) : AbstractUserRepositoryJdbcMssql(connection) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MSSQL_ROLE.label
                    from MSSQL_ROLE
                    ).fetchAll()
}

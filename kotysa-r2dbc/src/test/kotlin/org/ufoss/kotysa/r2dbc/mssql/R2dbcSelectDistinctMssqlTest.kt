/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectDistinctMssqlTest : AbstractR2dbcMssqlTest<UserRepositoryJdbcMssqlSelectDistinct>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMssqlSelectDistinct(connection)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() = runTest {
        assertThat(repository.selectDistinctRoleLabels().toList())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcMssqlSelectDistinct(connection: Connection) : AbstractUserRepositoryR2dbcMssql(connection) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MSSQL_ROLE.label
                    from MSSQL_ROLE
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectDistinctMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectDistinct>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMariadbSelectDistinct(connection)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() = runTest {
        assertThat(repository.selectDistinctRoleLabels().toList())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcMariadbSelectDistinct(connection: Connection) : AbstractUserRepositoryR2dbcMariadb(connection) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MARIADB_ROLE.label
                    from MARIADB_ROLE
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectDistinctH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2SelectDistinct>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcH2SelectDistinct(connection)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() = runTest {
        assertThat(repository.selectDistinctRoleLabels().toList())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcH2SelectDistinct(connection: Connection) : AbstractUserRepositoryR2dbcH2(connection) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct H2_ROLE.label
                    from H2_ROLE
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectDistinctPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectDistinct>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcPostgresqlSelectDistinct(connection)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() = runTest {
        assertThat(repository.selectDistinctRoleLabels().toList())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcPostgresqlSelectDistinct(connection: Connection) :
    AbstractUserRepositoryR2dbcPostgresql(connection) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct POSTGRESQL_ROLE.label
                    from POSTGRESQL_ROLE
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectDistinctMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlSelectDistinct>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMysqlSelectDistinct(connection)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() = runTest {
        assertThat(repository.selectDistinctRoleLabels().toList())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcMysqlSelectDistinct(connection: Connection) : AbstractUserRepositoryR2dbcMysql(connection) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MYSQL_ROLE.label
                    from MYSQL_ROLE
                    ).fetchAll()
}

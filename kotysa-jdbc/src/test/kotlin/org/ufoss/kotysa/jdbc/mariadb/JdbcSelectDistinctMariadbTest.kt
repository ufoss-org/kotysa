/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection


class JdbcSelectDistinctMariadbTest : AbstractJdbcMariadbTest<UserRepositoryJdbcMariadbSelectDistinct>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMariadbSelectDistinct(connection)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcMariadbSelectDistinct(connection: Connection) : AbstractUserRepositoryJdbcMariadb(connection) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MARIADB_ROLE.label
                    from MARIADB_ROLE
                    ).fetchAll()
}

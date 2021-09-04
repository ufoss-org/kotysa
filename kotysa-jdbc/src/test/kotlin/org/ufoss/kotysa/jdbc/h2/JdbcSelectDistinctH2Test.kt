/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection


class JdbcSelectDistinctH2Test : AbstractJdbcH2Test<UserRepositoryJdbcH2SelectDistinct>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcH2SelectDistinct(connection)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcH2SelectDistinct(connection: Connection) : AbstractUserRepositoryJdbcH2(connection) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct H2_ROLE.label
                    from H2_ROLE
                    ).fetchAll()
}

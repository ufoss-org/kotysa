/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection


class JdbcSelectDistinctPostgresqlTest : AbstractJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectDistinct>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcPostgresqlSelectDistinct(connection)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcPostgresqlSelectDistinct(connection: Connection) :
    AbstractUserRepositoryJdbcPostgresql(connection) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct POSTGRESQL_ROLE.label
                    from POSTGRESQL_ROLE
                    ).fetchAll()
}

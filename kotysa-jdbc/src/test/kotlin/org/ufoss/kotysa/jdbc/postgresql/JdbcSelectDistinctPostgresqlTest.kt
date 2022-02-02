/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectDistinctPostgresqlTest : AbstractJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectDistinct>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcPostgresqlSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcPostgresqlSelectDistinct(private val sqlClient: JdbcSqlClient) :
    AbstractUserRepositoryJdbcPostgresql(sqlClient) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct POSTGRESQL_ROLE.label
                    from POSTGRESQL_ROLE
                    ).fetchAll()
}

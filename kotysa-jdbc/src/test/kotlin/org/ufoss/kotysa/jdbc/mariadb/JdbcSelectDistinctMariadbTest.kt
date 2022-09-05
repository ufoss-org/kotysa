/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectDistinctMariadbTest : AbstractJdbcMariadbTest<UserRepositoryJdbcMariadbSelectDistinct>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMariadbSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcMariadbSelectDistinct(private val sqlClient: JdbcSqlClient) :
    AbstractUserRepositoryJdbcMariadb(sqlClient) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MariadbRoles.label
                    from MariadbRoles
                    ).fetchAll()
}

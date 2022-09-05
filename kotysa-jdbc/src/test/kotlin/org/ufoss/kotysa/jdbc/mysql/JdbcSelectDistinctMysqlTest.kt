/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectDistinctMysqlTest : AbstractJdbcMysqlTest<UserRepositoryJdbcMysqlSelectDistinct>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMysqlSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcMysqlSelectDistinct(private val sqlClient: JdbcSqlClient) :
    AbstractUserRepositoryJdbcMysql(sqlClient) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MysqlRoles.label
                    from MysqlRoles
                    ).fetchAll()
}

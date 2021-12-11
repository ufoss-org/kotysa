/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection


class JdbcSelectDistinctMysqlTest : AbstractJdbcMysqlTest<UserRepositoryJdbcMysqlSelectDistinct>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMysqlSelectDistinct(connection)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcMysqlSelectDistinct(connection: Connection) : AbstractUserRepositoryJdbcMysql(connection) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MYSQL_ROLE.label
                    from MYSQL_ROLE
                    ).fetchAll()
}
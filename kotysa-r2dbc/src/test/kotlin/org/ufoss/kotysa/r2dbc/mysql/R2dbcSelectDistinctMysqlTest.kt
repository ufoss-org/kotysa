/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser

class R2dbcSelectDistinctMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlSelectDistinct>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMysqlSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() = runTest {
        assertThat(repository.selectDistinctRoleLabels().toList())
            .hasSize(3)
            .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcMysqlSelectDistinct(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcMysql(sqlClient) {

    fun selectDistinctRoleLabels() =
        (sqlClient selectDistinct MysqlRoles.label
                from MysqlRoles
                ).fetchAll()
}

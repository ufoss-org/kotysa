/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcSelectDistinctMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectDistinct>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMariadbSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() = runTest {
        assertThat(repository.selectDistinctRoleLabels().toList())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcMariadbSelectDistinct(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcMariadb(sqlClient) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MariadbRoles.label
                    from MariadbRoles
                    ).fetchAll()
}

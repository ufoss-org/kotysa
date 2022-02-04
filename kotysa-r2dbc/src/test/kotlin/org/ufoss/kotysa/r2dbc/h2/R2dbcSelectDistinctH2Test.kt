/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser

class R2dbcSelectDistinctH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2SelectDistinct>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcH2SelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() = runTest {
        assertThat(repository.selectDistinctRoleLabels().toList())
            .hasSize(3)
            .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcH2SelectDistinct(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcH2(sqlClient) {

    fun selectDistinctRoleLabels() =
        (sqlClient selectDistinct H2Roles.label
                from H2Roles
                ).fetchAll()
}

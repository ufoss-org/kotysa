/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser

class R2dbcSelectDistinctPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectDistinct>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        UserRepositoryJdbcPostgresqlSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() = runTest {
        assertThat(repository.selectDistinctRoleLabels().toList())
            .hasSize(3)
            .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryJdbcPostgresqlSelectDistinct(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcPostgresql(sqlClient) {

    fun selectDistinctRoleLabels() =
        (sqlClient selectDistinct PostgresqlRoles.label
                from PostgresqlRoles
                ).fetchAll()
}

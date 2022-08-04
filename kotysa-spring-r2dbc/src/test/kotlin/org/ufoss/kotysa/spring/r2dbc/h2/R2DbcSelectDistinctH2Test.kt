/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser


class R2DbcSelectDistinctH2Test : AbstractR2dbcH2Test<UserRepositoryH2SelectDistinct>() {
    override val context = startContext<UserRepositoryH2SelectDistinct>()
    override val repository = getContextRepository<UserRepositoryH2SelectDistinct>()

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels().toIterable())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryH2SelectDistinct(sqlClient: ReactorSqlClient) : AbstractUserRepositoryH2(sqlClient) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct H2Roles.label
                    from H2Roles
                    ).fetchAll()
}

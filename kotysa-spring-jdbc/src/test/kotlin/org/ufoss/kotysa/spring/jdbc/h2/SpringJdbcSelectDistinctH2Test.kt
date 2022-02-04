/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*


class SpringJdbcSelectDistinctH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2SelectDistinct>() {
    override val context = startContext<UserRepositorySpringJdbcH2SelectDistinct>()
    override val repository = getContextRepository<UserRepositorySpringJdbcH2SelectDistinct>()

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositorySpringJdbcH2SelectDistinct(client: JdbcOperations) : AbstractUserRepositorySpringJdbcH2(client) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct H2Roles.label
                    from H2Roles
                    ).fetchAll()
}

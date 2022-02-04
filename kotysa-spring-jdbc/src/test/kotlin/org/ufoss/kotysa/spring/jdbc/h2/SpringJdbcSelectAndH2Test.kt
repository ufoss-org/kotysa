/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.roleUser


class SpringJdbcSelectAndH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2SelectAnd>() {
    override val context = startContext<UserRepositorySpringJdbcH2SelectAnd>()
    override val repository = getContextRepository<UserRepositorySpringJdbcH2SelectAnd>()

    @Test
    fun `Verify selectRolesByLabels finds h2User`() {
        assertThat(repository.selectRolesByLabels("u", "r"))
                .hasSize(1)
                .containsExactly(roleUser)
    }
}


class UserRepositorySpringJdbcH2SelectAnd(client: JdbcOperations) : AbstractUserRepositorySpringJdbcH2(client) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom H2Roles
                    where H2Roles.label contains label1
                    and H2Roles.label contains label2
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.H2_ROLE
import org.ufoss.kotysa.test.roleGod


class SpringJdbcSelectAndH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2SelectAnd>() {
    override val context = startContext<UserRepositorySpringJdbcH2SelectAnd>()

    override val repository = getContextRepository<UserRepositorySpringJdbcH2SelectAnd>()

    @Test
    fun `Verify selectRolesByLabels finds h2God`() {
        assertThat(repository.selectRolesByLabels("d", "g"))
                .hasSize(1)
                .containsExactly(roleGod)
    }
}


class UserRepositorySpringJdbcH2SelectAnd(client: JdbcOperations) : AbstractUserRepositorySpringJdbcH2(client) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom H2_ROLE
                    where H2_ROLE.label contains label1
                    and H2_ROLE.label contains label2
                    ).fetchAll()
}

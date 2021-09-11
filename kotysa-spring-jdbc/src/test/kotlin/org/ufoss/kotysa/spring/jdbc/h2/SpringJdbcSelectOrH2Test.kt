/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.H2_ROLE
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser


class SpringJdbcSelectOrH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2SelectOr>() {
    override val context = startContext<UserRepositorySpringJdbcH2SelectOr>()
    override val repository = getContextRepository<UserRepositorySpringJdbcH2SelectOr>()

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label))
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositorySpringJdbcH2SelectOr(client: JdbcOperations) : AbstractUserRepositorySpringJdbcH2(client) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom H2_ROLE
                    where H2_ROLE.label eq label1
                    or H2_ROLE.label eq label2
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.H2Role
import org.ufoss.kotysa.test.h2Admin
import org.ufoss.kotysa.test.h2God


class SpringJdbcSelectOrH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2SelectOr>() {
    override val context = startContext<UserRepositorySpringJdbcH2SelectOr>()

    override val repository = getContextRepository<UserRepositorySpringJdbcH2SelectOr>()

    @Test
    fun `Verify selectRolesByLabels finds h2Admin and h2God`() {
        assertThat(repository.selectRolesByLabels(h2Admin.label, h2God.label))
                .hasSize(2)
                .containsExactlyInAnyOrder(h2Admin, h2God)
    }
}


class UserRepositorySpringJdbcH2SelectOr(client: JdbcOperations) : AbstractUserRepositorySpringJdbcH2(client) {

    fun selectRolesByLabels(label1: String, label2: String) = sqlClient.select<H2Role>()
            .where { it[H2Role::label] eq label1 }
            .or { it[H2Role::label] eq label2 }
            .fetchAll()
}

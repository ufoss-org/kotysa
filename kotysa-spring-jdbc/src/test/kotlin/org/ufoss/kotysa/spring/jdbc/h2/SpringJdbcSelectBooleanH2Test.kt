/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.H2_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe


class SpringJdbcSelectBooleanH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2SelectBoolean>() {
    override val context = startContext<UserRepositorySpringJdbcH2SelectBoolean>()

    override val repository = getContextRepository<UserRepositorySpringJdbcH2SelectBoolean>()

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectAllByIsAdminEq(true))
                .hasSize(1)
                .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John`() {
        assertThat(repository.selectAllByIsAdminEq(false))
                .hasSize(1)
                .containsExactly(userJdoe)
    }
}


class UserRepositorySpringJdbcH2SelectBoolean(client: JdbcOperations) : AbstractUserRepositorySpringJdbcH2(client) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom H2_USER
                    where H2_USER.isAdmin eq value
                    ).fetchAll()
}

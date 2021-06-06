/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.MARIADB_USER
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe


class SpringJdbcSelectBooleanMariadbTest : AbstractSpringJdbcMariadbTest<UserRepositorySpringJdbcMariadbSelectBoolean>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMariadbSelectBoolean>(resource)
        repository = getContextRepository()
    }

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


class UserRepositorySpringJdbcMariadbSelectBoolean(client: JdbcOperations) : AbstractUserRepositorySpringJdbcMariadb(client) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.isAdmin eq value
                    ).fetchAll()
}

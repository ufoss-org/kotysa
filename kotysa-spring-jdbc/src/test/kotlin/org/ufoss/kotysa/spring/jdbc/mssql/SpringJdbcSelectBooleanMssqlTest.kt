/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe


class SpringJdbcSelectBooleanMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositorySpringJdbcMssqlSelectBoolean>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMssqlSelectBoolean>(resource)
    }

    override val repository: UserRepositorySpringJdbcMssqlSelectBoolean by lazy {
        getContextRepository()
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


class UserRepositorySpringJdbcMssqlSelectBoolean(client: JdbcOperations) : AbstractUserRepositorySpringJdbcMssql(client) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MssqlUsers
                    where MssqlUsers.isAdmin eq value
                    ).fetchAll()
}

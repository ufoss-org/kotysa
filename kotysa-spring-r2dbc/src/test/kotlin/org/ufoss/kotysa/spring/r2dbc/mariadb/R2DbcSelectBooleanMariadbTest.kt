/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe


class R2DbcSelectBooleanMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryMariadbSelectBoolean>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryMariadbSelectBoolean>(resource)
    }

    override val repository: UserRepositoryMariadbSelectBoolean by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectAllByIsAdminEq(true).toIterable())
                .hasSize(1)
                .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John`() {
        assertThat(repository.selectAllByIsAdminEq(false).toIterable())
                .hasSize(1)
                .containsExactly(userJdoe)
    }
}


class UserRepositoryMariadbSelectBoolean(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMariadb(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MariadbUsers
                    where MariadbUsers.isAdmin eq value
                    ).fetchAll()
}

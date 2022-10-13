/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class R2DbcSelectBooleanPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryPostgresqlSelectBoolean>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryPostgresqlSelectBoolean>(resource)
    }

    override val repository: UserRepositoryPostgresqlSelectBoolean by lazy {
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


class UserRepositoryPostgresqlSelectBoolean(sqlClient: ReactorSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom PostgresqlUsers
                    where PostgresqlUsers.isAdmin eq value
                    ).fetchAll()
}

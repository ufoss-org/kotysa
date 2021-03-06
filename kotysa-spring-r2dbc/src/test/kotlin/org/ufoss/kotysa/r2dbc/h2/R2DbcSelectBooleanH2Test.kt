/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe


class R2DbcSelectBooleanH2Test : AbstractR2dbcH2Test<UserRepositoryH2SelectBoolean>() {

    @BeforeAll
    fun beforeAll() {
        context = startContext<UserRepositoryH2SelectBoolean>()
        repository = getContextRepository()
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


class UserRepositoryH2SelectBoolean(
        sqlClient: ReactorSqlClient,
) : AbstractUserRepositoryH2(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom H2_USER
                    where H2_USER.isAdmin eq value
                    ).fetchAll()
}

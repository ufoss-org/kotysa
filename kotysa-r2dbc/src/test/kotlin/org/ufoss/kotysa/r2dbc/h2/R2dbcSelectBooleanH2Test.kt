/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe


class R2dbcSelectBooleanH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2SelectBoolean>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcH2SelectBoolean(sqlClient)

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() = runTest {
        assertThat(repository.selectAllByIsAdminEq(true).toList())
            .hasSize(1)
            .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John`() = runTest {
        assertThat(repository.selectAllByIsAdminEq(false).toList())
            .hasSize(1)
            .containsExactly(userJdoe)
    }
}


class UserRepositoryJdbcH2SelectBoolean(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcH2(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) =
        (sqlClient selectFrom H2Users
                where H2Users.isAdmin eq value
                ).fetchAll()
}

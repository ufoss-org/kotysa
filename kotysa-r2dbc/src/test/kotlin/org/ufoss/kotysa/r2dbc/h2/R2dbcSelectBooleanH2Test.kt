/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.H2_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe


class R2dbcSelectBooleanH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2SelectBoolean>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcH2SelectBoolean(connection)

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


class UserRepositoryJdbcH2SelectBoolean(connection: Connection) : AbstractUserRepositoryR2dbcH2(connection) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom H2_USER
                    where H2_USER.isAdmin eq value
                    ).fetchAll()
}

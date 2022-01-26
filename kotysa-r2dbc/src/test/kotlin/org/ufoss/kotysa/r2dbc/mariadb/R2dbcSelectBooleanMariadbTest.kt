/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MARIADB_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe

class R2dbcSelectBooleanMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryJdbcMariadbSelectBoolean>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMariadbSelectBoolean(connection)

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


class UserRepositoryJdbcMariadbSelectBoolean(connection: Connection) : AbstractUserRepositoryR2dbcMariadb(connection) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.isAdmin eq value
                    ).fetchAll()
}

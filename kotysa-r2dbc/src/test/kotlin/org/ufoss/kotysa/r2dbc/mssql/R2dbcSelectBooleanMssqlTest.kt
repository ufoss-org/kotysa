/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MSSQL_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe

class R2dbcSelectBooleanMssqlTest : AbstractR2dbcMssqlTest<UserRepositoryJdbcMssqlSelectBoolean>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMssqlSelectBoolean(connection)

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


class UserRepositoryJdbcMssqlSelectBoolean(connection: Connection) : AbstractUserRepositoryR2dbcMssql(connection) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MSSQL_USER
                    where MSSQL_USER.isAdmin eq value
                    ).fetchAll()
}

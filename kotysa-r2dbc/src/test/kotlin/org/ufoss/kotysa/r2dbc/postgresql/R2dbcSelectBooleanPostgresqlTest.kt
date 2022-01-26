/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

class R2dbcSelectBooleanPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectBoolean>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcPostgresqlSelectBoolean(connection)

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


class UserRepositoryJdbcPostgresqlSelectBoolean(connection: Connection) :
    AbstractUserRepositoryR2dbcPostgresql(connection) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom POSTGRESQL_USER
                    where POSTGRESQL_USER.isAdmin eq value
                    ).fetchAll()
}

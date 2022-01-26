/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MYSQL_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe

class R2dbcSelectBooleanMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryJdbcMysqlSelectBoolean>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMysqlSelectBoolean(connection)

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


class UserRepositoryJdbcMysqlSelectBoolean(connection: Connection) : AbstractUserRepositoryR2dbcMysql(connection) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MYSQL_USER
                    where MYSQL_USER.isAdmin eq value
                    ).fetchAll()
}

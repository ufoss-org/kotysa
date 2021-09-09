/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MSSQL_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe
import java.sql.Connection


class JdbcSelectBooleanMssqlTest : AbstractJdbcMssqlTest<UserRepositoryJdbcMssqlSelectBoolean>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMssqlSelectBoolean(connection)

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


class UserRepositoryJdbcMssqlSelectBoolean(connection: Connection) : AbstractUserRepositoryJdbcMssql(connection) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MSSQL_USER
                    where MSSQL_USER.isAdmin eq value
                    ).fetchAll()
}

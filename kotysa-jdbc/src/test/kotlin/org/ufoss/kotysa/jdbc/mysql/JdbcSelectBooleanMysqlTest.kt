/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.MYSQL_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe
import java.sql.Connection


class JdbcSelectBooleanMysqlTest : AbstractJdbcMysqlTest<UserRepositoryJdbcMysqlSelectBoolean>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcMysqlSelectBoolean(connection)

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


class UserRepositoryJdbcMysqlSelectBoolean(connection: Connection) : AbstractUserRepositoryJdbcMysql(connection) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MYSQL_USER
                    where MYSQL_USER.isAdmin eq value
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe

class JdbcSelectBooleanMysqlTest : AbstractJdbcMysqlTest<UserRepositoryJdbcMysqlSelectBoolean>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMysqlSelectBoolean(sqlClient)

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


class UserRepositoryJdbcMysqlSelectBoolean(private val sqlClient: JdbcSqlClient) : AbstractUserRepositoryJdbcMysql(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MysqlUsers
                    where MysqlUsers.isAdmin eq value
                    ).fetchAll()
}

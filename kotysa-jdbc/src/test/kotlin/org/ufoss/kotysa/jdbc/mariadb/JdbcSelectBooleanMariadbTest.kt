/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.MARIADB_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe

class JdbcSelectBooleanMariadbTest : AbstractJdbcMariadbTest<UserRepositoryJdbcMariadbSelectBoolean>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMariadbSelectBoolean(sqlClient)

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


class UserRepositoryJdbcMariadbSelectBoolean(private val sqlClient: JdbcSqlClient) :
    AbstractUserRepositoryJdbcMariadb(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MARIADB_USER
                    where MARIADB_USER.isAdmin eq value
                    ).fetchAll()
}

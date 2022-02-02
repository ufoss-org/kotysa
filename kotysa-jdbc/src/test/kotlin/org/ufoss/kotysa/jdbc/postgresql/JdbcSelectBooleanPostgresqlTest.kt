/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectBooleanPostgresqlTest : AbstractJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectBoolean>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcPostgresqlSelectBoolean(sqlClient)

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


class UserRepositoryJdbcPostgresqlSelectBoolean(private val sqlClient: JdbcSqlClient) :
    AbstractUserRepositoryJdbcPostgresql(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom POSTGRESQL_USER
                    where POSTGRESQL_USER.isAdmin eq value
                    ).fetchAll()
}

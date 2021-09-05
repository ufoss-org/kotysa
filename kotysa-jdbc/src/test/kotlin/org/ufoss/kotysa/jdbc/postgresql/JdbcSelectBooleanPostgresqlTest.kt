/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import java.sql.Connection


class JdbcSelectBooleanPostgresqlTest : AbstractJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlSelectBoolean>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcPostgresqlSelectBoolean(connection)

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


class UserRepositoryJdbcPostgresqlSelectBoolean(connection: Connection) :
    AbstractUserRepositoryJdbcPostgresql(connection) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom POSTGRESQL_USER
                    where POSTGRESQL_USER.isAdmin eq value
                    ).fetchAll()
}

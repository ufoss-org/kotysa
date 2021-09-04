/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.H2_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe
import java.sql.Connection


class JdbcSelectBooleanH2Test : AbstractJdbcH2Test<UserRepositoryJdbcH2SelectBoolean>() {
    override fun instantiateRepository(connection: Connection) = UserRepositoryJdbcH2SelectBoolean(connection)

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


class UserRepositoryJdbcH2SelectBoolean(connection: Connection) : AbstractUserRepositoryJdbcH2(connection) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom H2_USER
                    where H2_USER.isAdmin eq value
                    ).fetchAll()
}

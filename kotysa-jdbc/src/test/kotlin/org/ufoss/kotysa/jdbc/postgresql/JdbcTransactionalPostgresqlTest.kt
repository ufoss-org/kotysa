/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.userJdoe

class JdbcTransactionalPostgresqlTest : AbstractJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlTransactional>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcPostgresqlTransactional(sqlClient)

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss inside readonly transaction`() {
        val user = operator.transactional { transaction ->
            transaction.readOnly = true
            repository.selectFirstByFirstname("John")
        }
        assertThat(user)
            .isEqualTo(userJdoe)
    }
}


class UserRepositoryJdbcPostgresqlTransactional(sqlClient: JdbcSqlClient) : AbstractUserRepositoryJdbcPostgresql(sqlClient)

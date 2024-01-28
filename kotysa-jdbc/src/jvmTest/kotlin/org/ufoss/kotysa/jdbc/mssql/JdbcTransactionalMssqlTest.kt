/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.AbstractUserRepository

class JdbcTransactionalMssqlTest : AbstractJdbcMssqlTest<UserRepositoryJdbcMssqlTransactional>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMssqlTransactional(sqlClient)

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

class UserRepositoryJdbcMssqlTransactional(sqlClient: JdbcSqlClient) :
    AbstractUserRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )

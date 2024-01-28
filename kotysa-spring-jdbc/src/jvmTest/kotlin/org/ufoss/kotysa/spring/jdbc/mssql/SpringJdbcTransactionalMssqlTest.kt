/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.AbstractUserRepository

class SpringJdbcTransactionalMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositoryJdbcMssqldbTransactional>() {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositoryJdbcMssqldbTransactional(jdbcOperations)

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

class UserRepositoryJdbcMssqldbTransactional(client: JdbcOperations) :
    AbstractUserRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        client.sqlClient(mssqlTables),
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.AbstractUserRepository

class SpringJdbcTransactionalMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositoryJdbcMysqldbTransactional>() {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositoryJdbcMysqldbTransactional(jdbcOperations)

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

class UserRepositoryJdbcMysqldbTransactional(client: JdbcOperations) :
    AbstractUserRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        client.sqlClient(mysqlTables),
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.blocking.AbstractUserRepository
import org.ufoss.kotysa.test.userJdoe

class JdbcTransactionalMysqlTest : AbstractJdbcMysqlTest<UserRepositoryJdbcMysqlTransactional>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMysqlTransactional(sqlClient)

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

class UserRepositoryJdbcMysqlTransactional(sqlClient: JdbcSqlClient) :
    AbstractUserRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(sqlClient, MysqlRoles, MysqlUsers, MysqlUserRoles)
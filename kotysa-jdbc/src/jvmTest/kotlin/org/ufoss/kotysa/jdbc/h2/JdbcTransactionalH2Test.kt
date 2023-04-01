/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.blocking.AbstractUserRepository
import org.ufoss.kotysa.test.userJdoe

class JdbcTransactionalH2Test : AbstractJdbcH2Test<UserRepositoryJdbcH2Transactional>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcH2Transactional(sqlClient)

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

class UserRepositoryJdbcH2Transactional(sqlClient: JdbcSqlClient) :
    AbstractUserRepository<H2Roles, H2Users, H2UserRoles>(sqlClient, H2Roles, H2Users, H2UserRoles)

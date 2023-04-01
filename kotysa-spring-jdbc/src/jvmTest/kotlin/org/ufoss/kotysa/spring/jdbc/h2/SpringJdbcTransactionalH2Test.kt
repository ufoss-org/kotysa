/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.AbstractUserRepository

class SpringJdbcTransactionalH2Test : AbstractSpringJdbcH2Test<UserRepositoryJdbcH2Transactional>() {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositoryJdbcH2Transactional(jdbcOperations)

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

class UserRepositoryJdbcH2Transactional(client: JdbcOperations) :
    AbstractUserRepository<H2Roles, H2Users, H2UserRoles>(client.sqlClient(h2Tables), H2Roles, H2Users, H2UserRoles)

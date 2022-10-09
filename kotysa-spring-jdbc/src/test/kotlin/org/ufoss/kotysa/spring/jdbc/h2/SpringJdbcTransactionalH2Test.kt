/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.test.userJdoe

class SpringJdbcTransactionalH2Test : AbstractSpringJdbcH2Test<UserRepositoryJdbcH2Transactional>() {
    override val context = startContext<UserRepositoryJdbcH2Transactional>()
    override val repository = getContextRepository<UserRepositoryJdbcH2Transactional>()

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


class UserRepositoryJdbcH2Transactional(sqlClient: JdbcOperations) : AbstractUserRepositorySpringJdbcH2(sqlClient)

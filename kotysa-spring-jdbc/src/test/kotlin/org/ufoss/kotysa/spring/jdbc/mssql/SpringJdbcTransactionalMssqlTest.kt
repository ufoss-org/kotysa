/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.userJdoe

class SpringJdbcTransactionalMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositoryJdbcMssqldbTransactional>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryJdbcMssqldbTransactional>(resource)
    }

    override val repository: UserRepositoryJdbcMssqldbTransactional by lazy {
        getContextRepository()
    }

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


class UserRepositoryJdbcMssqldbTransactional(sqlClient: JdbcOperations)
    : AbstractUserRepositorySpringJdbcMssql(sqlClient)

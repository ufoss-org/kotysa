/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class SpringJdbcSelectDistinctMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlSelectDistinct>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMysqlSelectDistinct>(resource)
    }

    override val repository: UserRepositorySpringJdbcMysqlSelectDistinct by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositorySpringJdbcMysqlSelectDistinct(client: JdbcOperations) : AbstractUserRepositorySpringJdbcMysql(client) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MYSQL_ROLE.label
                    from MYSQL_ROLE
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.MYSQL_ROLE
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser


class SpringJdbcSelectOrMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlSelectOr>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMysqlSelectOr>(resource)
    }

    override val repository: UserRepositorySpringJdbcMysqlSelectOr by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label))
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositorySpringJdbcMysqlSelectOr(client: JdbcOperations) : AbstractUserRepositorySpringJdbcMysql(client) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom MYSQL_ROLE
                    where MYSQL_ROLE.label eq label1
                    or MYSQL_ROLE.label eq label2
                    ).fetchAll()
}

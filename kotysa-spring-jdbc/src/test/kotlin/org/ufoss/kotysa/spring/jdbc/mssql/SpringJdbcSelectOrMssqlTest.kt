/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.MSSQL_ROLE
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser


class SpringJdbcSelectOrMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositorySpringJdbcMysqlSelectOr>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMysqlSelectOr>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label))
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositorySpringJdbcMysqlSelectOr(client: JdbcOperations) : AbstractUserRepositorySpringJdbcMssql(client) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom MSSQL_ROLE
                    where MSSQL_ROLE.label eq label1
                    or MSSQL_ROLE.label eq label2
                    ).fetchAll()
}

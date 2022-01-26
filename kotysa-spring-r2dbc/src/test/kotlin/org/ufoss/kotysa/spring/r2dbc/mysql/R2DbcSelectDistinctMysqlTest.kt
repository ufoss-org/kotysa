/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.MYSQL_ROLE
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser


class R2DbcSelectDistinctMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryMysqlSelectDistinct>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryMysqlSelectDistinct>(resource)
    }

    override val repository: UserRepositoryMysqlSelectDistinct by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels().toIterable())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryMysqlSelectDistinct(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MYSQL_ROLE.label
                    from MYSQL_ROLE
                    ).fetchAll()
}

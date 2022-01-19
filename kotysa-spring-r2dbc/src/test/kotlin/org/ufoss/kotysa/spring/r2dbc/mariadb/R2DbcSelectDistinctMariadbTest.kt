/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.spring.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.MARIADB_ROLE
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser


class R2DbcSelectDistinctMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryMariadbSelectDistinct>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryMariadbSelectDistinct>(resource)
    }

    override val repository: UserRepositoryMariadbSelectDistinct by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels().toIterable())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryMariadbSelectDistinct(sqlClient: ReactorSqlClient) : org.ufoss.kotysa.spring.r2dbc.mariadb.AbstractUserRepositoryMariadb(sqlClient) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct MARIADB_ROLE.label
                    from MARIADB_ROLE
                    ).fetchAll()
}

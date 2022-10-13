/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser


class R2DbcSelectOrMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryMysqlSelectOr>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryMysqlSelectOr>(resource)
    }

    override val repository: UserRepositoryMysqlSelectOr by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryMysqlSelectOr(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom MysqlRoles
                    where MysqlRoles.label eq label1
                    or MysqlRoles.label eq label2
                    ).fetchAll()
}

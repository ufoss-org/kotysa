/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser


class R2DbcSelectOrPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryPostgresqlSelectOr>() {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = UserRepositoryPostgresqlSelectOr(sqlClient)

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryPostgresqlSelectOr(sqlClient: ReactorSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
        (sqlClient selectFrom PostgresqlRoles
                where PostgresqlRoles.label eq label1
                or PostgresqlRoles.label eq label2
                ).fetchAll()
}
/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient


class VertxSqlClientSelectOrPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<UserRepositoryPostgresqlSelectOr>() {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = UserRepositoryPostgresqlSelectOr(sqlClient)

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryPostgresqlSelectOr(sqlClient: MutinyVertxSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
        (sqlClient selectFrom PostgresqlRoles
                where PostgresqlRoles.label eq label1
                or PostgresqlRoles.label eq label2
                ).fetchAll()
}

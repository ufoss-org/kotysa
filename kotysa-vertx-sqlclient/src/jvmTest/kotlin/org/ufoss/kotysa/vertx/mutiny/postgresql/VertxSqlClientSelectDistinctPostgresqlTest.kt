/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient


class VertxSqlClientSelectDistinctPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<UserRepositoryPostgresqlSelectDistinct>() {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = UserRepositoryPostgresqlSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels().await().indefinitely())
            .hasSize(3)
            .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryPostgresqlSelectDistinct(sqlClient: MutinyVertxSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectDistinctRoleLabels() =
        (sqlClient selectDistinct PostgresqlRoles.label
                from PostgresqlRoles
                ).fetchAll()
}

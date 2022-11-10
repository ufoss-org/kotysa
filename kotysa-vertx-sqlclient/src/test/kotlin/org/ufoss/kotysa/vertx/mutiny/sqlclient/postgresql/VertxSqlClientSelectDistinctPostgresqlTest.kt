/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.PostgresqlRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser
import org.ufoss.kotysa.vertx.mutiny.sqlclient.PostgresqlVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient


class VertxSqlClientSelectDistinctPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<UserRepositoryPostgresqlSelectDistinct>() {

    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = UserRepositoryPostgresqlSelectDistinct(sqlClient)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels().await().indefinitely())
            .hasSize(3)
            .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}


class UserRepositoryPostgresqlSelectDistinct(sqlClient: VertxSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectDistinctRoleLabels() =
        (sqlClient selectDistinct PostgresqlRoles.label
                from PostgresqlRoles
                ).fetchAll()
}

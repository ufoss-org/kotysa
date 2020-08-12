/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.test.PostgresqlRole
import org.ufoss.kotysa.test.postgresqlAdmin
import org.ufoss.kotysa.test.postgresqlGod


class R2DbcSelectOrPostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryPostgresqlSelectOr>() {
    override val context = startContext<UserRepositoryPostgresqlSelectOr>()

    override val repository = getContextRepository<UserRepositoryPostgresqlSelectOr>()

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() {
        assertThat(repository.selectRolesByLabels(postgresqlAdmin.label, postgresqlGod.label).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(postgresqlAdmin, postgresqlGod)
    }
}


class UserRepositoryPostgresqlSelectOr(dbClient: DatabaseClient) : AbstractUserRepositoryPostgresql(dbClient) {

    fun selectRolesByLabels(label1: String, label2: String) = sqlClient.select<PostgresqlRole>()
            .where { it[PostgresqlRole::label] eq label1 }
            .or { it[PostgresqlRole::label] eq label2 }
            .fetchAll()
}
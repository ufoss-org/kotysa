/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.ufoss.kotysa.test.PostgresqlRole
import org.ufoss.kotysa.test.postgresqlAdmin
import org.ufoss.kotysa.test.postgresqlGod


class SpringJdbcSelectOrPostgresqlTest : AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlSelectOr>() {
    override val context = startContext<UserRepositorySpringJdbcPostgresqlSelectOr>()

    override val repository = getContextRepository<UserRepositorySpringJdbcPostgresqlSelectOr>()

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() {
        assertThat(repository.selectRolesByLabels(postgresqlAdmin.label, postgresqlGod.label))
                .hasSize(2)
                .containsExactlyInAnyOrder(postgresqlAdmin, postgresqlGod)
    }
}


class UserRepositorySpringJdbcPostgresqlSelectOr(client: JdbcTemplate) : AbstractUserRepositorySpringJdbcPostgresql(client) {

    fun selectRolesByLabels(label1: String, label2: String) = sqlClient.select<PostgresqlRole>()
            .where { it[PostgresqlRole::label] eq label1 }
            .or { it[PostgresqlRole::label] eq label2 }
            .fetchAll()
}
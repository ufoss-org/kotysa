/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.PostgresqlUser
import org.ufoss.kotysa.test.postgresqlBboss
import org.ufoss.kotysa.test.postgresqlJdoe


class SpringJdbcSelectBooleanH2Test : AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlSelectBoolean>() {
    override val context = startContext<UserRepositorySpringJdbcPostgresqlSelectBoolean>()

    override val repository = getContextRepository<UserRepositorySpringJdbcPostgresqlSelectBoolean>()

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectAllByIsAdminEq(true))
                .hasSize(1)
                .containsExactly(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John`() {
        assertThat(repository.selectAllByIsAdminEq(false))
                .hasSize(1)
                .containsExactly(postgresqlJdoe)
    }
}


class UserRepositorySpringJdbcPostgresqlSelectBoolean(client: JdbcOperations) : AbstractUserRepositorySpringJdbcPostgresql(client) {

    fun selectAllByIsAdminEq(value: Boolean) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::isAdmin] eq value }
            .fetchAll()
}

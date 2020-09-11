/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.PostgresqlUser
import org.ufoss.kotysa.test.postgresqlBboss
import org.ufoss.kotysa.test.postgresqlJdoe


class R2DbcSelectBooleanH2Test : AbstractR2dbcPostgresqlTest<UserRepositoryPostgresqlSelectBoolean>() {
    override val context = startContext<UserRepositoryPostgresqlSelectBoolean>()

    override val repository = getContextRepository<UserRepositoryPostgresqlSelectBoolean>()

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectAllByIsAdminEq(true).toIterable())
                .hasSize(1)
                .containsExactly(postgresqlBboss)
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John`() {
        assertThat(repository.selectAllByIsAdminEq(false).toIterable())
                .hasSize(1)
                .containsExactly(postgresqlJdoe)
    }
}


class UserRepositoryPostgresqlSelectBoolean(sqlClient: ReactorSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::isAdmin] eq value }
            .fetchAll()
}

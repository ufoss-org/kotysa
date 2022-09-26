/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient


class VertxSqlClientSelectBooleanPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<UserRepositoryPostgresqlSelectBoolean>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositoryPostgresqlSelectBoolean(sqlClient)

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectAllByIsAdminEq(true).await().indefinitely())
                .hasSize(1)
                .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John`() {
        assertThat(repository.selectAllByIsAdminEq(false).await().indefinitely())
                .hasSize(1)
                .containsExactly(userJdoe)
    }
}


class UserRepositoryPostgresqlSelectBoolean(sqlClient: VertxSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom PostgresqlUsers
                    where PostgresqlUsers.isAdmin eq value
                    ).fetchAll()
}

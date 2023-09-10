/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient


class VertxSqlClientSelectBooleanMssqlTest : AbstractVertxSqlClientMssqlTest<UserRepositoryMssqlSelectBoolean>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryMssqlSelectBoolean(sqlClient)

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


class UserRepositoryMssqlSelectBoolean(sqlClient: MutinyVertxSqlClient) : AbstractUserRepositoryMssql(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MssqlUsers
                    where MssqlUsers.isAdmin eq value
                    ).fetchAll()
}

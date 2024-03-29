/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient


class VertxSqlClientSelectBooleanMysqlTest : AbstractVertxSqlClientMysqlTest<UserRepositoryMysqlSelectBoolean>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryMysqlSelectBoolean(sqlClient)

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


class UserRepositoryMysqlSelectBoolean(sqlClient: MutinyVertxSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MysqlUsers
                    where MysqlUsers.isAdmin eq value
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient


class VertxSqlClientSelectBooleanMariadbTest : AbstractVertxSqlClientMariadbTest<UserRepositoryMariadbSelectBoolean>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UserRepositoryMariadbSelectBoolean(sqlClient)

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


class UserRepositoryMariadbSelectBoolean(sqlClient: MutinyVertxSqlClient) : AbstractUserRepositoryMariadb(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom MariadbUsers
                    where MariadbUsers.isAdmin eq value
                    ).fetchAll()
}

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*


class R2DbcSelectBooleanMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryMyqlSelectBoolean>() {
    override val context = startContext<UserRepositoryMyqlSelectBoolean>()

    override val repository = getContextRepository<UserRepositoryMyqlSelectBoolean>()

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectAllByIsAdminEq(true).toIterable())
                .hasSize(1)
                .containsExactly(mysqlBboss)
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John`() {
        assertThat(repository.selectAllByIsAdminEq(false).toIterable())
                .hasSize(1)
                .containsExactly(mysqlJdoe)
    }
}


class UserRepositoryMyqlSelectBoolean(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun selectAllByIsAdminEq(value: Boolean) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::isAdmin] eq value }
            .fetchAll()
}

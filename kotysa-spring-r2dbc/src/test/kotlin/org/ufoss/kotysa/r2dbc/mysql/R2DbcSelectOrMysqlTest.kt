/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*


class R2DbcSelectOrMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryMysqlSelectOr>() {
    override val context = startContext<UserRepositoryMysqlSelectOr>()

    override val repository = getContextRepository<UserRepositoryMysqlSelectOr>()

    @Test
    fun `Verify selectRolesByLabels finds mysqlAdmin and mysqlGod`() {
        assertThat(repository.selectRolesByLabels(mysqlAdmin.label, mysqlGod.label).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(mysqlAdmin, mysqlGod)
    }
}


class UserRepositoryMysqlSelectOr(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) = sqlClient.select<MysqlRole>()
            .where { it[MysqlRole::label] eq label1 }
            .or { it[MysqlRole::label] eq label2 }
            .fetchAll()
}

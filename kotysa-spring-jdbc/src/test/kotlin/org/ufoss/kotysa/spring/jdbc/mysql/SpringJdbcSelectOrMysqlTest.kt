/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*


class SpringJdbcSelectOrMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlSelectOr>() {
    override val context = startContext<UserRepositorySpringJdbcMysqlSelectOr>()

    override val repository = getContextRepository<UserRepositorySpringJdbcMysqlSelectOr>()

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() {
        assertThat(repository.selectRolesByLabels(mysqlAdmin.label, mysqlGod.label))
                .hasSize(2)
                .containsExactlyInAnyOrder(mysqlAdmin, mysqlGod)
    }
}


class UserRepositorySpringJdbcMysqlSelectOr(client: JdbcOperations) : AbstractUserRepositorySpringJdbcMysql(client) {

    fun selectRolesByLabels(label1: String, label2: String) = sqlClient.select<MysqlRole>()
            .where { it[MysqlRole::label] eq label1 }
            .or { it[MysqlRole::label] eq label2 }
            .fetchAll()
}

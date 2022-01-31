/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.MSSQL_ROLE
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleUser

class JdbcSelectOrMssqlTest : AbstractJdbcMssqlTest<UserRepositoryJdbcMysqlSelectOr>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMysqlSelectOr(sqlClient)

    @Test
    fun `Verify selectRolesByLabels finds postgresqlAdmin and postgresqlGod`() {
        assertThat(repository.selectRolesByLabels(roleUser.label, roleAdmin.label))
                .hasSize(2)
                .containsExactlyInAnyOrder(roleUser, roleAdmin)
    }
}


class UserRepositoryJdbcMysqlSelectOr(private val sqlClient: JdbcSqlClient) : AbstractUserRepositoryJdbcMssql(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom MSSQL_ROLE
                    where MSSQL_ROLE.label eq label1
                    or MSSQL_ROLE.label eq label2
                    ).fetchAll()
}

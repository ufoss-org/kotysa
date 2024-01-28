/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlCompanies
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectTest
import java.sql.SQLSyntaxErrorException

class JdbcSelectMysqlTest : AbstractJdbcMysqlTest<UserRepositoryJdbcMysqlSelect>(),
    SelectTest<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies, UserRepositoryJdbcMysqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMysqlSelect(sqlClient)

    @Test
    override fun `Verify selectWithCascadeFullJoin works correctly`() {
        assertThatThrownBy { super.`Verify selectWithCascadeFullJoin works correctly`() }
            .isInstanceOf(SQLSyntaxErrorException::class.java)
    }
}

class UserRepositoryJdbcMysqlSelect(sqlClient: JdbcSqlClient) :
    SelectRepository<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles,
        MysqlCompanies
    )

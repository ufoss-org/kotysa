/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.jdbc.BadSqlGrammarException
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectTest

class SpringJdbcSelectMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlSelect>(),
    SelectTest<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies, UserRepositorySpringJdbcMysqlSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMysqlSelect(jdbcOperations)

    @Test
    override fun `Verify selectWithCascadeFullJoin works correctly`() {
        assertThatThrownBy { super.`Verify selectWithCascadeFullJoin works correctly`() }
            .isInstanceOf(BadSqlGrammarException::class.java)
    }
}

class UserRepositorySpringJdbcMysqlSelect(client: JdbcOperations) :
    SelectRepository<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies>(
        client.sqlClient(mysqlTables),
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles,
        MysqlCompanies
    )

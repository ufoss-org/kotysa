/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.BadSqlGrammarException
import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlCompanies
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectTest

class R2DbcSelectMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryMysqlSelect>(),
    ReactorSelectTest<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies, UserRepositoryMysqlSelect,
            ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        UserRepositoryMysqlSelect(sqlClient)

    @Test
    override fun `Verify selectWithCascadeFullJoin works correctly`() {
        assertThatThrownBy { super.`Verify selectWithCascadeFullJoin works correctly`() }
            .isInstanceOf(BadSqlGrammarException::class.java)
    }
}

class UserRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectRepository<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles,
        MysqlCompanies
    )

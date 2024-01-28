/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SubQueryRepository
import org.ufoss.kotysa.test.repositories.blocking.SubQueryTest

class SpringJdbcSubQueryOracleTest : AbstractSpringJdbcOracleTest<UserRepositoryJdbcOracleSubQuery>(),
    SubQueryTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies, UserRepositoryJdbcOracleSubQuery,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositoryJdbcOracleSubQuery(jdbcOperations)
}

class UserRepositoryJdbcOracleSubQuery(client: JdbcOperations) :
    SubQueryRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        client.sqlClient(oracleTables),
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )

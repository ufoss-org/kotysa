/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class SpringJdbcSelectAndOracleTest : AbstractSpringJdbcOracleTest<UserRepositorySpringJdbcOracleSelectAnd>(),
    SelectAndTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies, UserRepositorySpringJdbcOracleSelectAnd,
            SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcOracleSelectAnd(jdbcOperations)
}

class UserRepositorySpringJdbcOracleSelectAnd(client: JdbcOperations) :
    SelectAndRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        client.sqlClient(oracleTables),
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )

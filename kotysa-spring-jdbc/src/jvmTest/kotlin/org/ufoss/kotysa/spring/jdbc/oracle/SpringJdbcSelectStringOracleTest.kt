/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class SpringJdbcSelectStringOracleTest : AbstractSpringJdbcOracleTest<UserRepositorySpringJdbcOracleSelectString>(),
    SelectStringTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies,
            UserRepositorySpringJdbcOracleSelectString, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcOracleSelectString(jdbcOperations)
}

class UserRepositorySpringJdbcOracleSelectString(client: JdbcOperations) :
    SelectStringRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        client.sqlClient(oracleTables),
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )

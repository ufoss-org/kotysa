/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanTest

class SpringJdbcSelectBooleanOracleTest : AbstractSpringJdbcOracleTest<UserRepositorySpringJdbcOracleSelectBoolean>(),
    SelectBooleanTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies,
            UserRepositorySpringJdbcOracleSelectBoolean, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcOracleSelectBoolean(jdbcOperations)
}

class UserRepositorySpringJdbcOracleSelectBoolean(client: JdbcOperations) :
    SelectBooleanRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        client.sqlClient(oracleTables),
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )

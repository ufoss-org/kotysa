/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectOrRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrTest

class SpringJdbcSelectOrOracleTest : AbstractSpringJdbcOracleTest<UserRepositorySpringJdbcOracleSelectOr>(),
    SelectOrTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies, UserRepositorySpringJdbcOracleSelectOr,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcOracleSelectOr(jdbcOperations)
}

class UserRepositorySpringJdbcOracleSelectOr(client: JdbcOperations) :
    SelectOrRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        client.sqlClient(oracleTables),
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )

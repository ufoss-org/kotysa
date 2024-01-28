/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctTest

class SpringJdbcSelectDistinctOracleTest : AbstractSpringJdbcOracleTest<UserRepositorySpringJdbcOracleSelectDistinct>(),
    SelectDistinctTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies,
            UserRepositorySpringJdbcOracleSelectDistinct, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcOracleSelectDistinct(jdbcOperations)
}

class UserRepositorySpringJdbcOracleSelectDistinct(client: JdbcOperations) :
    SelectDistinctRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        client.sqlClient(oracleTables),
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )

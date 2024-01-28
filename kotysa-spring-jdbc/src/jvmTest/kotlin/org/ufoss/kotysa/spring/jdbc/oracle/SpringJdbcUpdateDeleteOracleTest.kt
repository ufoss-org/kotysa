/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.oracle

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteTest

class SpringJdbcUpdateDeleteOracleTest : AbstractSpringJdbcOracleTest<UserRepositorySpringJdbcOracleUpdateDelete>(),
    UpdateDeleteTest<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies,
            UserRepositorySpringJdbcOracleUpdateDelete, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcOracleUpdateDelete(jdbcOperations)
}

class UserRepositorySpringJdbcOracleUpdateDelete(client: JdbcOperations) :
    UpdateDeleteRepository<OracleRoles, OracleUsers, OracleUserRoles, OracleCompanies>(
        client.sqlClient(oracleTables),
        OracleRoles,
        OracleUsers,
        OracleUserRoles,
        OracleCompanies
    )

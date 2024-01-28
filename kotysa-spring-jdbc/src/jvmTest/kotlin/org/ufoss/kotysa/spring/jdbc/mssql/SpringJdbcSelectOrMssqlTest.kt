/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectOrRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrTest

class SpringJdbcSelectOrMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositorySpringJdbcMssqlSelectOr>(),
    SelectOrTest<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies, UserRepositorySpringJdbcMssqlSelectOr,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMssqlSelectOr(jdbcOperations)
}

class UserRepositorySpringJdbcMssqlSelectOr(client: JdbcOperations) :
    SelectOrRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        client.sqlClient(mssqlTables),
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )

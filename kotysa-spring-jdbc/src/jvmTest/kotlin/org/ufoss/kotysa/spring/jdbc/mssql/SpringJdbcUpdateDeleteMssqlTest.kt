/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteTest

class SpringJdbcUpdateDeleteMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositorySpringJdbcMssqlUpdateDelete>(),
    UpdateDeleteTest<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies, UserRepositorySpringJdbcMssqlUpdateDelete,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMssqlUpdateDelete(jdbcOperations)
}

class UserRepositorySpringJdbcMssqlUpdateDelete(client: JdbcOperations) :
    UpdateDeleteRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        client.sqlClient(mssqlTables),
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )

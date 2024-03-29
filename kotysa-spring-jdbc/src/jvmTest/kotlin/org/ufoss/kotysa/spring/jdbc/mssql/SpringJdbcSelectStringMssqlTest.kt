/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class SpringJdbcSelectStringMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositorySpringJdbcMssqlSelectString>(),
    SelectStringTest<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies, UserRepositorySpringJdbcMssqlSelectString,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMssqlSelectString(jdbcOperations)
}

class UserRepositorySpringJdbcMssqlSelectString(client: JdbcOperations) :
    SelectStringRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        client.sqlClient(mssqlTables),
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )

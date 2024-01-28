/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanTest

class SpringJdbcSelectBooleanMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositorySpringJdbcMssqlSelectBoolean>(),
    SelectBooleanTest<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies,
            UserRepositorySpringJdbcMssqlSelectBoolean, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMssqlSelectBoolean(jdbcOperations)
}

class UserRepositorySpringJdbcMssqlSelectBoolean(client: JdbcOperations) :
    SelectBooleanRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        client.sqlClient(mssqlTables),
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )

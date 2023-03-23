/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mssql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class SpringJdbcSelectAndMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositorySpringJdbcMssqlSelectAnd>(),
    SelectAndTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositorySpringJdbcMssqlSelectAnd,
            SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMssqlSelectAnd(jdbcOperations)
}

class UserRepositorySpringJdbcMssqlSelectAnd(client: JdbcOperations) :
    SelectAndRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        client.sqlClient(mssqlTables),
        MssqlRoles, MssqlUsers, MssqlUserRoles
    )

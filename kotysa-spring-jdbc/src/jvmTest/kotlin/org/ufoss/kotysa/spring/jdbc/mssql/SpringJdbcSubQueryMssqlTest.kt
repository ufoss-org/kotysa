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
import org.ufoss.kotysa.test.repositories.blocking.SubQueryRepository
import org.ufoss.kotysa.test.repositories.blocking.SubQueryTest

class SpringJdbcSubQueryMssqlTest : AbstractSpringJdbcMssqlTest<UserRepositoryJdbcMssqlSubQuery>(),
    SubQueryTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryJdbcMssqlSubQuery, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = UserRepositoryJdbcMssqlSubQuery(jdbcOperations)
}

class UserRepositoryJdbcMssqlSubQuery(client: JdbcOperations) :
    SubQueryRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        client.sqlClient(mssqlTables),
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )

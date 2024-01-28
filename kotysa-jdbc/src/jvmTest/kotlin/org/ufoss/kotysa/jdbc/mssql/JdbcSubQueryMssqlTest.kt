/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlCompanies
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.blocking.SubQueryRepository
import org.ufoss.kotysa.test.repositories.blocking.SubQueryTest

class JdbcSubQueryMssqlTest : AbstractJdbcMssqlTest<UserRepositoryJdbcMssqlSubQuery>(),
    SubQueryTest<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies, UserRepositoryJdbcMssqlSubQuery,
            JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMssqlSubQuery(sqlClient)
}

class UserRepositoryJdbcMssqlSubQuery(sqlClient: JdbcSqlClient) :
    SubQueryRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryTest

class R2dbcSubQueryMssqlTest : AbstractR2dbcMssqlTest<UserRepositoryR2dbcMssqlSubQuery>(),
    CoroutinesSubQueryTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryR2dbcMssqlSubQuery,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryR2dbcMssqlSubQuery(sqlClient)
}

class UserRepositoryR2dbcMssqlSubQuery(sqlClient: R2dbcSqlClient) :
    CoroutinesSubQueryRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )

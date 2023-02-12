/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDistinctRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDistinctTest

class R2dbcSelectDistinctMssqlTest : AbstractR2dbcMssqlTest<UserRepositoryJdbcMssqlSelectDistinct>(),
    CoroutinesSelectDistinctTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryJdbcMssqlSelectDistinct,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMssqlSelectDistinct(sqlClient)
}

class UserRepositoryJdbcMssqlSelectDistinct(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectDistinctRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )

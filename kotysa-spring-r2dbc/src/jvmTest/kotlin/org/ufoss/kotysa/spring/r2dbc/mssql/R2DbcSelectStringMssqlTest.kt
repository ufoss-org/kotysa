/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectStringTest

class R2dbcSelectStringMssqlTest : AbstractR2dbcMssqlTest<UserRepositoryR2dbcMssqlSelectString>(),
    ReactorSelectStringTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryR2dbcMssqlSelectString,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        UserRepositoryR2dbcMssqlSelectString(sqlClient)
}

class UserRepositoryR2dbcMssqlSelectString(sqlClient: ReactorSqlClient) :
    ReactorSelectStringRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )

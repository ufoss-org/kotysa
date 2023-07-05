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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectTest

class R2DbcSelectMssqlTest : AbstractR2dbcMssqlTest<UserRepositoryMssqlSelect>(),
    ReactorSelectTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryMssqlSelect, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        UserRepositoryMssqlSelect(sqlClient)
}

class UserRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )

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
import org.ufoss.kotysa.test.repositories.reactor.ReactorUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorUpdateDeleteTest

class R2dbcUpdateDeleteMssqlTest : AbstractR2dbcMssqlTest<UserRepositoryR2dbcMssqlUpdateDelete>(),
    ReactorUpdateDeleteTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryR2dbcMssqlUpdateDelete,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        UserRepositoryR2dbcMssqlUpdateDelete(sqlClient)
}

class UserRepositoryR2dbcMssqlUpdateDelete(sqlClient: ReactorSqlClient) :
    ReactorUpdateDeleteRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )

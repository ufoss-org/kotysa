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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrTest

class R2dbcSelectOrMssqlTest : AbstractR2dbcMssqlTest<UserRepositoryR2dbcMssqlSelectOr>(),
    ReactorSelectOrTest<MssqlRoles, MssqlUsers, MssqlUserRoles, UserRepositoryR2dbcMssqlSelectOr,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        UserRepositoryR2dbcMssqlSelectOr(sqlClient)
}

class UserRepositoryR2dbcMssqlSelectOr(sqlClient: ReactorSqlClient) :
    ReactorSelectOrRepository<MssqlRoles, MssqlUsers, MssqlUserRoles>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles
    )

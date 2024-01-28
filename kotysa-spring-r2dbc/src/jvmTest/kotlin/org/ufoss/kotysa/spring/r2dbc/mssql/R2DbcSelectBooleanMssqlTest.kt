/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlCompanies
import org.ufoss.kotysa.test.MssqlRoles
import org.ufoss.kotysa.test.MssqlUserRoles
import org.ufoss.kotysa.test.MssqlUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBooleanRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBooleanTest

class R2dbcSelectBooleanMssqlTest : AbstractR2dbcMssqlTest<ReactorUserRepositoryMssqlSelectBoolean>(),
    ReactorSelectBooleanTest<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies,
            ReactorUserRepositoryMssqlSelectBoolean, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        ReactorUserRepositoryMssqlSelectBoolean(sqlClient)
}

class ReactorUserRepositoryMssqlSelectBoolean(sqlClient: ReactorSqlClient) :
    ReactorSelectBooleanRepository<MssqlRoles, MssqlUsers, MssqlUserRoles, MssqlCompanies>(
        sqlClient,
        MssqlRoles,
        MssqlUsers,
        MssqlUserRoles,
        MssqlCompanies
    )

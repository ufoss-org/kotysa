/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlUuids
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectUuidRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectUuidTest

class R2dbcSelectUuidMssqlTest : AbstractR2dbcMssqlTest<UuidRepositoryMssqlSelect>(),
    ReactorSelectUuidTest<MssqlUuids, UuidRepositoryMssqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        UuidRepositoryMssqlSelect(sqlClient)
}

class UuidRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectUuidRepository<MssqlUuids>(sqlClient, MssqlUuids)

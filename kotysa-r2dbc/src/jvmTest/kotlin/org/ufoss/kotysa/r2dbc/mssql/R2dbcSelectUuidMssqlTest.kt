/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlUuids
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectUuidRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectUuidTest

class R2dbcSelectUuidMssqlTest : AbstractR2dbcMssqlTest<UuidRepositoryMssqlSelect>(),
    CoroutinesSelectUuidTest<MssqlUuids, UuidRepositoryMssqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UuidRepositoryMssqlSelect(sqlClient)
}

class UuidRepositoryMssqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectUuidRepository<MssqlUuids>(sqlClient, MssqlUuids)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlUuids
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectUuidRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectUuidTest

class VertxCoroutinesSelectUuidMssqlTest : AbstractVertxCoroutinesMssqlTest<UuidRepositoryMssqlSelect>(),
    CoroutinesSelectUuidTest<MssqlUuids, UuidRepositoryMssqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = UuidRepositoryMssqlSelect(sqlClient)
}

class UuidRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectUuidRepository<MssqlUuids>(sqlClient, MssqlUuids)

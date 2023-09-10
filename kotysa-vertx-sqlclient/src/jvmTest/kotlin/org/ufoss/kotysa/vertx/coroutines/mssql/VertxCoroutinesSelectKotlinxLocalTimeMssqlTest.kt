/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeTest

class VertxCoroutinesSelectKotlinxLocalTimeMssqlTest : AbstractVertxCoroutinesMssqlTest<KotlinxLocalTimeRepositoryMssqlSelect>(),
    CoroutinesSelectKotlinxLocalTimeTest<MssqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryMssqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = KotlinxLocalTimeRepositoryMssqlSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectKotlinxLocalTimeRepository<MssqlKotlinxLocalTimes>(sqlClient, MssqlKotlinxLocalTimes)

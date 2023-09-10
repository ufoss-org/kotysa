/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlKotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeTest


class VertxCoroutinesSelectKotlinxLocalDateTimeMssqlTest : AbstractVertxCoroutinesMssqlTest<KotlinxLocalDateTimeRepositoryMssqlSelect>(),
    CoroutinesSelectKotlinxLocalDateTimeTest<MssqlKotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryMssqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = KotlinxLocalDateTimeRepositoryMssqlSelect(sqlClient)
}

class KotlinxLocalDateTimeRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectKotlinxLocalDateTimeRepository<MssqlKotlinxLocalDateTimes>(sqlClient, MssqlKotlinxLocalDateTimes)

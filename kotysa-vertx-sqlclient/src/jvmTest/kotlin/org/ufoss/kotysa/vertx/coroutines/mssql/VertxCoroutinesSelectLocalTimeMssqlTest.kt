/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalTimeTest


class VertxCoroutinesSelectLocalTimeMssqlTest : AbstractVertxCoroutinesMssqlTest<LocalTimeRepositoryMssqlSelect>(),
    CoroutinesSelectLocalTimeTest<MssqlLocalTimes, LocalTimeRepositoryMssqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LocalTimeRepositoryMssqlSelect(sqlClient)
}

class LocalTimeRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLocalTimeRepository<MssqlLocalTimes>(sqlClient, MssqlLocalTimes)

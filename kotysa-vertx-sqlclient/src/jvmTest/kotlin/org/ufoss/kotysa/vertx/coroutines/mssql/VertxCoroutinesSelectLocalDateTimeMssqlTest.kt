/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlLocalDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeTest

class VertxCoroutinesSelectLocalDateTimeMssqlTest : AbstractVertxCoroutinesMssqlTest<LocalDateTimeRepositoryMssqlSelect>(),
    CoroutinesSelectLocalDateTimeTest<MssqlLocalDateTimes, LocalDateTimeRepositoryMssqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = LocalDateTimeRepositoryMssqlSelect(sqlClient)
}

class LocalDateTimeRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLocalDateTimeRepository<MssqlLocalDateTimes>(sqlClient, MssqlLocalDateTimes)

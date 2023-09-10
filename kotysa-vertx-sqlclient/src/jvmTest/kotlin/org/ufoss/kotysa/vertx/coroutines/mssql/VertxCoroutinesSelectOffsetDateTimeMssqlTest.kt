/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlOffsetDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOffsetDateTimeTest

class VertxCoroutinesSelectOffsetDateTimeMssqlTest : AbstractVertxCoroutinesMssqlTest<OffsetDateTimeRepositoryMssqlSelect>(),
    CoroutinesSelectOffsetDateTimeTest<MssqlOffsetDateTimes, OffsetDateTimeRepositoryMssqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = OffsetDateTimeRepositoryMssqlSelect(sqlClient)
}

class OffsetDateTimeRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectOffsetDateTimeRepository<MssqlOffsetDateTimes>(sqlClient, MssqlOffsetDateTimes)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleOffsetDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOffsetDateTimeTest

class VertxCoroutinesSelectOffsetDateTimeOracleTest : AbstractVertxCoroutinesOracleTest<OffsetDateTimeRepositoryOracleSelect>(),
    CoroutinesSelectOffsetDateTimeTest<OracleOffsetDateTimes, OffsetDateTimeRepositoryOracleSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = OffsetDateTimeRepositoryOracleSelect(sqlClient)
}

class OffsetDateTimeRepositoryOracleSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectOffsetDateTimeRepository<OracleOffsetDateTimes>(sqlClient, OracleOffsetDateTimes)

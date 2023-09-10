/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeAsTimestampTest


class VertxCoroutinesSelectLocalDateTimeAsTimestampOracleTest : AbstractVertxCoroutinesOracleTest<LocalDateTimeAsTimestampRepositoryOracleSelect>(),
    CoroutinesSelectLocalDateTimeAsTimestampTest<OracleLocalDateTimeAsTimestamps, LocalDateTimeAsTimestampRepositoryOracleSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) =
        LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient)
}

class LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLocalDateTimeAsTimestampRepository<OracleLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleLocalDateTimeAsTimestamps,
    )

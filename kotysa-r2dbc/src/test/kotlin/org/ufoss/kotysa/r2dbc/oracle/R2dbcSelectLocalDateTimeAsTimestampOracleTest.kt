/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeAsTimestampTest


class R2dbcSelectLocalDateTimeAsTimestampOracleTest : AbstractR2dbcOracleTest<LocalDateTimeAsTimestampRepositoryOracleSelect>(),
    CoroutinesSelectLocalDateTimeAsTimestampTest<OracleLocalDateTimeAsTimestamps, LocalDateTimeAsTimestampRepositoryOracleSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient)
}

class LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLocalDateTimeAsTimestampRepository<OracleLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleLocalDateTimeAsTimestamps,
    )

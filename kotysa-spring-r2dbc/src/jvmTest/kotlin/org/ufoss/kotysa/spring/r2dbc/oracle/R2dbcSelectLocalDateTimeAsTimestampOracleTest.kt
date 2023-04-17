/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeAsTimestampTest

class R2dbcSelectLocalDateTimeAsTimestampOracleTest :
    AbstractR2dbcOracleTest<LocalDateTimeAsTimestampRepositoryOracleSelect>(),
    ReactorSelectLocalDateTimeAsTimestampTest<OracleLocalDateTimeAsTimestamps,
            LocalDateTimeAsTimestampRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient)
}

class LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateTimeAsTimestampRepository<OracleLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleLocalDateTimeAsTimestamps
    )

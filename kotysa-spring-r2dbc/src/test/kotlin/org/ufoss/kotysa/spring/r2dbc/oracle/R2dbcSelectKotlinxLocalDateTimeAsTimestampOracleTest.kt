/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.oracle

import org.ufoss.kotysa.OracleCoroutinesSqlClient
import org.ufoss.kotysa.OracleReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.OracleKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeAsTimestampTest

class JdbcSelectKotlinxLocalDateTimeAsTimestampOracleTest :
    AbstractR2dbcOracleTest<KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect>(),
    ReactorSelectKotlinxLocalDateTimeAsTimestampTest<OracleKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: OracleReactorSqlClient, coSqlClient: OracleCoroutinesSqlClient) =
        KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient)
}

class KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateTimeAsTimestampRepository<OracleKotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleKotlinxLocalDateTimeAsTimestamps
    )

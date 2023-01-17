/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.oracle

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.OracleKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeAsTimestampTest


class R2dbcSelectKotlinxLocalDateTimeAsTimestampOracleTest :
    AbstractR2dbcOracleTest<KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect>(),
    CoroutinesSelectKotlinxLocalDateTimeAsTimestampTest<OracleKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) =
        KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient)
}

class KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalDateTimeAsTimestampRepository<OracleKotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleKotlinxLocalDateTimeAsTimestamps
    )

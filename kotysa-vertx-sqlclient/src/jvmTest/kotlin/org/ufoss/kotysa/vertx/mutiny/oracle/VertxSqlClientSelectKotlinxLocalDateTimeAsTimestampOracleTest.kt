/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectKotlinxLocalDateTimeAsTimestampTest

class VertxSqlClientSelectKotlinxLocalDateTimeAsTimestampOracleTest :
    AbstractVertxSqlClientOracleTest<KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect>(),
    MutinySelectKotlinxLocalDateTimeAsTimestampTest<OracleKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient)
}

class KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectKotlinxLocalDateTimeAsTimestampRepository<OracleKotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleKotlinxLocalDateTimeAsTimestamps
    )

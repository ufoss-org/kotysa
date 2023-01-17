/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectKotlinxLocalDateTimeAsTimestampTest

class VertxSqlClientSelectKotlinxLocalDateTimeAsTimestampOracleTest :
    AbstractVertxSqlClientOracleTest<KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect>(),
    MutinySelectKotlinxLocalDateTimeAsTimestampTest<OracleKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) =
        KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient)
}

class KotlinxLocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectKotlinxLocalDateTimeAsTimestampRepository<OracleKotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleKotlinxLocalDateTimeAsTimestamps
    )

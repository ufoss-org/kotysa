/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleLocalDateTimeAsTimestamps
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLocalDateTimeAsTimestampTest

class VertxSqlClientSelectLocalDateTimeAsTimestampOracleTest :
    AbstractVertxSqlClientOracleTest<LocalDateTimeAsTimestampRepositoryOracleSelect>(),
    MutinySelectLocalDateTimeAsTimestampTest<OracleLocalDateTimeAsTimestamps,
            LocalDateTimeAsTimestampRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient)
}

class LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectLocalDateTimeAsTimestampRepository<OracleLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleLocalDateTimeAsTimestamps
    )

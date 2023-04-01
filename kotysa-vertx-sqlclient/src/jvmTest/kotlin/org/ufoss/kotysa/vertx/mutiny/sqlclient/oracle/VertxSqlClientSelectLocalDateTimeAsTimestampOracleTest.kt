/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleLocalDateTimeAsTimestamps
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectLocalDateTimeAsTimestampTest

class VertxSqlClientSelectLocalDateTimeAsTimestampOracleTest :
    AbstractVertxSqlClientOracleTest<LocalDateTimeAsTimestampRepositoryOracleSelect>(),
    MutinySelectLocalDateTimeAsTimestampTest<OracleLocalDateTimeAsTimestamps,
            LocalDateTimeAsTimestampRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) =
        LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient)
}

class LocalDateTimeAsTimestampRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectLocalDateTimeAsTimestampRepository<OracleLocalDateTimeAsTimestamps>(
        sqlClient,
        OracleLocalDateTimeAsTimestamps
    )

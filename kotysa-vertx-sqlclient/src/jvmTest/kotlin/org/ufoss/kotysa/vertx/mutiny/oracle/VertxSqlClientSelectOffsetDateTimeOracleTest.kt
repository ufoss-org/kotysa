/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleOffsetDateTimes
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectOffsetDateTimeRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectOffsetDateTimeTest

class VertxSqlClientSelectOffsetDateTimeOracleTest :
    AbstractVertxSqlClientOracleTest<OffsetDateTimeRepositoryOracleSelect>(),
    MutinySelectOffsetDateTimeTest<OracleOffsetDateTimes, OffsetDateTimeRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        OffsetDateTimeRepositoryOracleSelect(sqlClient)
}

class OffsetDateTimeRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectOffsetDateTimeRepository<OracleOffsetDateTimes>(sqlClient, OracleOffsetDateTimes)

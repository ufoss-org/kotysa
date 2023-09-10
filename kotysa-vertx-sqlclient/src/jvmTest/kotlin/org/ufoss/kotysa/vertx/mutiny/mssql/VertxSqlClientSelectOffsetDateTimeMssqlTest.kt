/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlOffsetDateTimes
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectOffsetDateTimeRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectOffsetDateTimeTest

class VertxSqlClientSelectOffsetDateTimeMssqlTest :
    AbstractVertxSqlClientMssqlTest<OffsetDateTimeRepositoryMssqlSelect>(),
    MutinySelectOffsetDateTimeTest<MssqlOffsetDateTimes, OffsetDateTimeRepositoryMssqlSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        OffsetDateTimeRepositoryMssqlSelect(sqlClient)
}

class OffsetDateTimeRepositoryMssqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectOffsetDateTimeRepository<MssqlOffsetDateTimes>(sqlClient, MssqlOffsetDateTimes)

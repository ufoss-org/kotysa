/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlOffsetDateTimes
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectOffsetDateTimeRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectOffsetDateTimeTest

class VertxSqlClientSelectOffsetDateTimeMssqlTest :
    AbstractVertxSqlClientMssqlTest<OffsetDateTimeRepositoryMssqlSelect>(),
    MutinySelectOffsetDateTimeTest<MssqlOffsetDateTimes, OffsetDateTimeRepositoryMssqlSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) =
        OffsetDateTimeRepositoryMssqlSelect(sqlClient)
}

class OffsetDateTimeRepositoryMssqlSelect(sqlClient: VertxSqlClient) :
    MutinySelectOffsetDateTimeRepository<MssqlOffsetDateTimes>(sqlClient, MssqlOffsetDateTimes)

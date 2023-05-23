/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlLocalTimes
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectLocalTimeRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectLocalTimeTest

class VertxSqlClientSelectLocalTimeMssqlTest :
    AbstractVertxSqlClientMssqlTest<LocalTimeMssqlRepository>(),
    MutinySelectLocalTimeTest<MssqlLocalTimes, LocalTimeMssqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = LocalTimeMssqlRepository(sqlClient)
}

class LocalTimeMssqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectLocalTimeRepository<MssqlLocalTimes>(sqlClient, MssqlLocalTimes)

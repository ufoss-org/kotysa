/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlLocalTimes
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLocalTimeRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLocalTimeTest

class VertxSqlClientSelectLocalTimeMssqlTest :
    AbstractVertxSqlClientMssqlTest<LocalTimeMssqlRepository>(),
    MutinySelectLocalTimeTest<MssqlLocalTimes, LocalTimeMssqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = LocalTimeMssqlRepository(sqlClient)
}

class LocalTimeMssqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectLocalTimeRepository<MssqlLocalTimes>(sqlClient, MssqlLocalTimes)

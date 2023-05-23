/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlKotlinxLocalTimes
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectKotlinxLocalTimeTest

class VertxSqlClientSelectKotlinxLocalTimeMssqlTest :
    AbstractVertxSqlClientMssqlTest<KotlinxLocalTimeMssqlRepository>(),
    MutinySelectKotlinxLocalTimeTest<MssqlKotlinxLocalTimes, KotlinxLocalTimeMssqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = KotlinxLocalTimeMssqlRepository(sqlClient)
}

class KotlinxLocalTimeMssqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectKotlinxLocalTimeRepository<MssqlKotlinxLocalTimes>(sqlClient, MssqlKotlinxLocalTimes)

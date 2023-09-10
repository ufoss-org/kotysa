/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlKotlinxLocalTimes
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectKotlinxLocalTimeTest

class VertxSqlClientSelectKotlinxLocalTimeMssqlTest :
    AbstractVertxSqlClientMssqlTest<KotlinxLocalTimeMssqlRepository>(),
    MutinySelectKotlinxLocalTimeTest<MssqlKotlinxLocalTimes, KotlinxLocalTimeMssqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = KotlinxLocalTimeMssqlRepository(sqlClient)
}

class KotlinxLocalTimeMssqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectKotlinxLocalTimeRepository<MssqlKotlinxLocalTimes>(sqlClient, MssqlKotlinxLocalTimes)

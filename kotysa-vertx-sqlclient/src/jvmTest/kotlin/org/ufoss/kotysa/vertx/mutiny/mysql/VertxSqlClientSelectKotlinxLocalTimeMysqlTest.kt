/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.ufoss.kotysa.test.MysqlKotlinxLocalTimes
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectKotlinxLocalTimeTest
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectKotlinxLocalTimeMysqlTest :
    AbstractVertxSqlClientMysqlTest<KotlinxLocalTimeMysqlRepository>(),
    MutinySelectKotlinxLocalTimeTest<MysqlKotlinxLocalTimes, KotlinxLocalTimeMysqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = KotlinxLocalTimeMysqlRepository(sqlClient)
}

class KotlinxLocalTimeMysqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectKotlinxLocalTimeRepository<MysqlKotlinxLocalTimes>(sqlClient, MysqlKotlinxLocalTimes)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.ufoss.kotysa.test.MysqlKotlinxLocalTimes
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectKotlinxLocalTimeTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectKotlinxLocalTimeMysqlTest :
    AbstractVertxSqlClientMysqlTest<KotlinxLocalTimeMysqlRepository>(),
    MutinySelectKotlinxLocalTimeTest<MysqlKotlinxLocalTimes, KotlinxLocalTimeMysqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = KotlinxLocalTimeMysqlRepository(sqlClient)
}

class KotlinxLocalTimeMysqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectKotlinxLocalTimeRepository<MysqlKotlinxLocalTimes>(sqlClient, MysqlKotlinxLocalTimes)

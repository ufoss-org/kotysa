/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.ufoss.kotysa.test.PostgresqlKotlinxLocalTimes
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectKotlinxLocalTimeTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectKotlinxLocalTimePostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<KotlinxLocalTimePostgresqlRepository>(),
    MutinySelectKotlinxLocalTimeTest<PostgresqlKotlinxLocalTimes, KotlinxLocalTimePostgresqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = KotlinxLocalTimePostgresqlRepository(sqlClient)
}

class KotlinxLocalTimePostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectKotlinxLocalTimeRepository<PostgresqlKotlinxLocalTimes>(sqlClient, PostgresqlKotlinxLocalTimes)

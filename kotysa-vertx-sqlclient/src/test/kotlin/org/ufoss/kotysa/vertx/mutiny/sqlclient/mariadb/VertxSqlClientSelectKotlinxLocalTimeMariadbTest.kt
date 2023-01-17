/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.ufoss.kotysa.test.MariadbKotlinxLocalTimes
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectKotlinxLocalTimeTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectKotlinxLocalTimeMariadbTest :
    AbstractVertxSqlClientMariadbTest<KotlinxLocalTimeMariadbRepository>(),
    MutinySelectKotlinxLocalTimeTest<MariadbKotlinxLocalTimes, KotlinxLocalTimeMariadbRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = KotlinxLocalTimeMariadbRepository(sqlClient)
}

class KotlinxLocalTimeMariadbRepository(sqlClient: MutinySqlClient) :
    MutinySelectKotlinxLocalTimeRepository<MariadbKotlinxLocalTimes>(sqlClient, MariadbKotlinxLocalTimes)

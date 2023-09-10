/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.ufoss.kotysa.test.MariadbKotlinxLocalTimes
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectKotlinxLocalTimeTest
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectKotlinxLocalTimeMariadbTest :
    AbstractVertxSqlClientMariadbTest<KotlinxLocalTimeMariadbRepository>(),
    MutinySelectKotlinxLocalTimeTest<MariadbKotlinxLocalTimes, KotlinxLocalTimeMariadbRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = KotlinxLocalTimeMariadbRepository(sqlClient)
}

class KotlinxLocalTimeMariadbRepository(sqlClient: MutinySqlClient) :
    MutinySelectKotlinxLocalTimeRepository<MariadbKotlinxLocalTimes>(sqlClient, MariadbKotlinxLocalTimes)

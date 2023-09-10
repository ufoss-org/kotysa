/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.ufoss.kotysa.test.MariadbDoubles
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectDoubleRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectDoubleTest
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectDoubleMariadbTest :
    AbstractVertxSqlClientMariadbTest<DoubleMariadbRepository>(),
    MutinySelectDoubleTest<MariadbDoubles, DoubleMariadbRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = DoubleMariadbRepository(sqlClient)
}

class DoubleMariadbRepository(sqlClient: MutinySqlClient) :
    MutinySelectDoubleRepository<MariadbDoubles>(sqlClient, MariadbDoubles)

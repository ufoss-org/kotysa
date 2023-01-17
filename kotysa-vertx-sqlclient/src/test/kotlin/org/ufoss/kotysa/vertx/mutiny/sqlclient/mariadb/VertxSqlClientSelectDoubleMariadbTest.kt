/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.ufoss.kotysa.test.MariadbDoubles
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectDoubleRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectDoubleTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectDoubleMariadbTest :
    AbstractVertxSqlClientMariadbTest<DoubleMariadbRepository>(),
    MutinySelectDoubleTest<MariadbDoubles, DoubleMariadbRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = DoubleMariadbRepository(sqlClient)
}

class DoubleMariadbRepository(sqlClient: MutinySqlClient) :
    MutinySelectDoubleRepository<MariadbDoubles>(sqlClient, MariadbDoubles)

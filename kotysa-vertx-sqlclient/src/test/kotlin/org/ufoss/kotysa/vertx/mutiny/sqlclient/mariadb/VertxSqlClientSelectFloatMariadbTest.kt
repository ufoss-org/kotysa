/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.ufoss.kotysa.test.MariadbFloats
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectFloatRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectFloatTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectFloatMariadbTest :
    AbstractVertxSqlClientMariadbTest<FloatMariadbRepository>(),
    MutinySelectFloatTest<MariadbFloats, FloatMariadbRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = FloatMariadbRepository(sqlClient)
}

class FloatMariadbRepository(sqlClient: MutinySqlClient) :
    MutinySelectFloatRepository<MariadbFloats>(sqlClient, MariadbFloats)

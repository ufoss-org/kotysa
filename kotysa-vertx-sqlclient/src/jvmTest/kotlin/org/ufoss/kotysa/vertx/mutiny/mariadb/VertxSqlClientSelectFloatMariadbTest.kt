/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.ufoss.kotysa.test.MariadbFloats
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectFloatRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectFloatTest
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectFloatMariadbTest :
    AbstractVertxSqlClientMariadbTest<FloatMariadbRepository>(),
    MutinySelectFloatTest<MariadbFloats, FloatMariadbRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = FloatMariadbRepository(sqlClient)
}

class FloatMariadbRepository(sqlClient: MutinySqlClient) :
    MutinySelectFloatRepository<MariadbFloats>(sqlClient, MariadbFloats)

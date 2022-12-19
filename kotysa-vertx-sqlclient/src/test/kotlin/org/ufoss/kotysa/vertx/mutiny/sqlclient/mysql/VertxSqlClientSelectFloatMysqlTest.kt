/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.ufoss.kotysa.test.MysqlFloats
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectFloatRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectFloatTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectFloatMysqlTest :
    AbstractVertxSqlClientMysqlTest<FloatMysqlRepository>(),
    MutinySelectFloatTest<MysqlFloats, FloatMysqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = FloatMysqlRepository(sqlClient)
}

class FloatMysqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectFloatRepository<MysqlFloats>(sqlClient, MysqlFloats)

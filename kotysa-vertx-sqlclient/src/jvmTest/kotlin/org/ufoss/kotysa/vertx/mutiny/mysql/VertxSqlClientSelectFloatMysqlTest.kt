/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.ufoss.kotysa.test.MysqlFloats
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectFloatRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectFloatTest
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectFloatMysqlTest :
    AbstractVertxSqlClientMysqlTest<FloatMysqlRepository>(),
    MutinySelectFloatTest<MysqlFloats, FloatMysqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = FloatMysqlRepository(sqlClient)
}

class FloatMysqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectFloatRepository<MysqlFloats>(sqlClient, MysqlFloats)

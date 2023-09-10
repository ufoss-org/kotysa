/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.ufoss.kotysa.test.MysqlDoubles
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectDoubleRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectDoubleTest
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectDoubleMysqlTest :
    AbstractVertxSqlClientMysqlTest<DoubleMysqlRepository>(),
    MutinySelectDoubleTest<MysqlDoubles, DoubleMysqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = DoubleMysqlRepository(sqlClient)
}

class DoubleMysqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectDoubleRepository<MysqlDoubles>(sqlClient, MysqlDoubles)

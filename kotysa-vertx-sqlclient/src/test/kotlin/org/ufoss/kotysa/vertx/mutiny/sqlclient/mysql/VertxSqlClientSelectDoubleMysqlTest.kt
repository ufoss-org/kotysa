/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.ufoss.kotysa.test.MysqlDoubles
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectDoubleRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectDoubleTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectDoubleMysqlTest :
    AbstractVertxSqlClientMysqlTest<DoubleMysqlRepository>(),
    MutinySelectDoubleTest<MysqlDoubles, DoubleMysqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = DoubleMysqlRepository(sqlClient)
}

class DoubleMysqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectDoubleRepository<MysqlDoubles>(sqlClient, MysqlDoubles)

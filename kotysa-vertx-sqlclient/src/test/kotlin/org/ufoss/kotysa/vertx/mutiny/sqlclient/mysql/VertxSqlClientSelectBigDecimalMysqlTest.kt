/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.ufoss.kotysa.test.MysqlBigDecimals
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectBigDecimalRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectBigDecimalTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectBigDecimalMysqlTest :
    AbstractVertxSqlClientMysqlTest<BigDecimalMysqlRepository>(),
    MutinySelectBigDecimalTest<MysqlBigDecimals, BigDecimalMysqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = BigDecimalMysqlRepository(sqlClient)
}

class BigDecimalMysqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalRepository<MysqlBigDecimals>(sqlClient, MysqlBigDecimals)

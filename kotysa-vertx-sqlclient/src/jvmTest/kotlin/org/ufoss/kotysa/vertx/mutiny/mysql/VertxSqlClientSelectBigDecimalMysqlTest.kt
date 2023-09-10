/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.ufoss.kotysa.test.MysqlBigDecimals
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalTest
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectBigDecimalMysqlTest :
    AbstractVertxSqlClientMysqlTest<BigDecimalMysqlRepository>(),
    MutinySelectBigDecimalTest<MysqlBigDecimals, BigDecimalMysqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = BigDecimalMysqlRepository(sqlClient)
}

class BigDecimalMysqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalRepository<MysqlBigDecimals>(sqlClient, MysqlBigDecimals)

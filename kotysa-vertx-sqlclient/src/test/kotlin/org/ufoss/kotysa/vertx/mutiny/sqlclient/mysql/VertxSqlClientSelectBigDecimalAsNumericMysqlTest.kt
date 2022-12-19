/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mysql

import org.ufoss.kotysa.test.MysqlBigDecimalAsNumerics
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectBigDecimalAsNumericTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectBigDecimalAsNumericMysqlTest :
    AbstractVertxSqlClientMysqlTest<BigDecimalAsNumericMysqlRepository>(),
    MutinySelectBigDecimalAsNumericTest<MysqlBigDecimalAsNumerics, BigDecimalAsNumericMysqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = BigDecimalAsNumericMysqlRepository(sqlClient)
}

class BigDecimalAsNumericMysqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalAsNumericRepository<MysqlBigDecimalAsNumerics>(sqlClient, MysqlBigDecimalAsNumerics)

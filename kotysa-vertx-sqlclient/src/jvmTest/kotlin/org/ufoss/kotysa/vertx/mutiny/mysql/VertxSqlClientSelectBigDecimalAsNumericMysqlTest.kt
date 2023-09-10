/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.ufoss.kotysa.test.MysqlBigDecimalAsNumerics
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalAsNumericTest
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectBigDecimalAsNumericMysqlTest :
    AbstractVertxSqlClientMysqlTest<BigDecimalAsNumericMysqlRepository>(),
    MutinySelectBigDecimalAsNumericTest<MysqlBigDecimalAsNumerics, BigDecimalAsNumericMysqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = BigDecimalAsNumericMysqlRepository(sqlClient)
}

class BigDecimalAsNumericMysqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalAsNumericRepository<MysqlBigDecimalAsNumerics>(sqlClient, MysqlBigDecimalAsNumerics)

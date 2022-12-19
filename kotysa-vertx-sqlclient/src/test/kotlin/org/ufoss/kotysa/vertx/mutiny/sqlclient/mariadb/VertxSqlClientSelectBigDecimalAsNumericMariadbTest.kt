/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.ufoss.kotysa.test.MariadbBigDecimalAsNumerics
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectBigDecimalAsNumericTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectBigDecimalAsNumericMariadbTest :
    AbstractVertxSqlClientMariadbTest<BigDecimalAsNumericMariadbRepository>(),
    MutinySelectBigDecimalAsNumericTest<MariadbBigDecimalAsNumerics, BigDecimalAsNumericMariadbRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = BigDecimalAsNumericMariadbRepository(sqlClient)
}

class BigDecimalAsNumericMariadbRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalAsNumericRepository<MariadbBigDecimalAsNumerics>(sqlClient, MariadbBigDecimalAsNumerics)

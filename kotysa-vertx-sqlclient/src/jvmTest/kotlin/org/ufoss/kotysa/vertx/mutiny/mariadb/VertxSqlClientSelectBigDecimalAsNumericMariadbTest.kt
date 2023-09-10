/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.ufoss.kotysa.test.MariadbBigDecimalAsNumerics
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalAsNumericTest
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectBigDecimalAsNumericMariadbTest :
    AbstractVertxSqlClientMariadbTest<BigDecimalAsNumericMariadbRepository>(),
    MutinySelectBigDecimalAsNumericTest<MariadbBigDecimalAsNumerics, BigDecimalAsNumericMariadbRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = BigDecimalAsNumericMariadbRepository(sqlClient)
}

class BigDecimalAsNumericMariadbRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalAsNumericRepository<MariadbBigDecimalAsNumerics>(sqlClient, MariadbBigDecimalAsNumerics)

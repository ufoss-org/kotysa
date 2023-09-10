/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.ufoss.kotysa.test.MariadbBigDecimals
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalTest
import org.ufoss.kotysa.vertx.MutinySqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectBigDecimalMariadbTest :
    AbstractVertxSqlClientMariadbTest<BigDecimalMariadbRepository>(),
    MutinySelectBigDecimalTest<MariadbBigDecimals, BigDecimalMariadbRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = BigDecimalMariadbRepository(sqlClient)
}

class BigDecimalMariadbRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalRepository<MariadbBigDecimals>(sqlClient, MariadbBigDecimals)

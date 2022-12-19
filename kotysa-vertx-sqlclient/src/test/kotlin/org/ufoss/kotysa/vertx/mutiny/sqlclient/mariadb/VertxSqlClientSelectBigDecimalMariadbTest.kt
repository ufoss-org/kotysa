/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.ufoss.kotysa.test.MariadbBigDecimals
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectBigDecimalRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySelectBigDecimalTest
import org.ufoss.kotysa.vertx.mutiny.sqlclient.MutinySqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectBigDecimalMariadbTest :
    AbstractVertxSqlClientMariadbTest<BigDecimalMariadbRepository>(),
    MutinySelectBigDecimalTest<MariadbBigDecimals, BigDecimalMariadbRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = BigDecimalMariadbRepository(sqlClient)
}

class BigDecimalMariadbRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalRepository<MariadbBigDecimals>(sqlClient, MariadbBigDecimals)

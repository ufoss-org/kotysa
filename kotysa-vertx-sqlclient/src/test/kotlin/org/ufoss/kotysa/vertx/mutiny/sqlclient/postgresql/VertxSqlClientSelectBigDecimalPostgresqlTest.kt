/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.ufoss.kotysa.test.PostgresqlBigDecimals
import org.ufoss.kotysa.vertx.mutiny.sqlclient.*

class VertxSqlClientSelectBigDecimalPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<BigDecimalPostgresqlRepository>(),
    MutinySelectBigDecimalTest<PostgresqlBigDecimals, BigDecimalPostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = BigDecimalPostgresqlRepository(sqlClient)
}

class BigDecimalPostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalRepository<PostgresqlBigDecimals>(sqlClient, PostgresqlBigDecimals)

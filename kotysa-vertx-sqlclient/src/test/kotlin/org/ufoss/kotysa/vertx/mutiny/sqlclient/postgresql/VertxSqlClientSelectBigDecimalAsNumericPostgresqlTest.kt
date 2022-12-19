/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.ufoss.kotysa.test.PostgresqlBigDecimalAsNumerics
import org.ufoss.kotysa.vertx.mutiny.sqlclient.*

class VertxSqlClientSelectBigDecimalAsNumericPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<BigDecimalAsNumericPostgresqlRepository>(),
    MutinySelectBigDecimalAsNumericTest<PostgresqlBigDecimalAsNumerics, BigDecimalAsNumericPostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = BigDecimalAsNumericPostgresqlRepository(sqlClient)
}

class BigDecimalAsNumericPostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalAsNumericRepository<PostgresqlBigDecimalAsNumerics>(sqlClient, PostgresqlBigDecimalAsNumerics)

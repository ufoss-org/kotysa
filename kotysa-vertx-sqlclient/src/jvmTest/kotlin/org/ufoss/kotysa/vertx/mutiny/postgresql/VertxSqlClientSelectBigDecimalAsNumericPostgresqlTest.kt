/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.test.PostgresqlBigDecimalAsNumerics
import org.ufoss.kotysa.vertx.*
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalAsNumericTest

class VertxSqlClientSelectBigDecimalAsNumericPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<BigDecimalAsNumericPostgresqlRepository>(),
    MutinySelectBigDecimalAsNumericTest<PostgresqlBigDecimalAsNumerics, BigDecimalAsNumericPostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = BigDecimalAsNumericPostgresqlRepository(sqlClient)
}

class BigDecimalAsNumericPostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalAsNumericRepository<PostgresqlBigDecimalAsNumerics>(sqlClient, PostgresqlBigDecimalAsNumerics)

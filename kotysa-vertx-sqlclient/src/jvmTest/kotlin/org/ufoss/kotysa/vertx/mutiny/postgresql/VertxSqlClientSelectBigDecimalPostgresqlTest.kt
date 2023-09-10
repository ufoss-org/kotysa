/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.test.PostgresqlBigDecimals
import org.ufoss.kotysa.vertx.*
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalTest

class VertxSqlClientSelectBigDecimalPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<BigDecimalPostgresqlRepository>(),
    MutinySelectBigDecimalTest<PostgresqlBigDecimals, BigDecimalPostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = BigDecimalPostgresqlRepository(sqlClient)
}

class BigDecimalPostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalRepository<PostgresqlBigDecimals>(sqlClient, PostgresqlBigDecimals)

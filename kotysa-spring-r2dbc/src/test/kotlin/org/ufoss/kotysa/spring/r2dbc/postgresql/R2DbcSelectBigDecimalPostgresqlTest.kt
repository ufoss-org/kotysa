/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlBigDecimals
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalTest

class R2DbcSelectBigDecimalPostgresqlTest : AbstractR2dbcPostgresqlTest<BigDecimalPostgresqlRepository>(),
    ReactorSelectBigDecimalTest<PostgresqlBigDecimals, BigDecimalPostgresqlRepository,
            ReactorTransaction> {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = BigDecimalPostgresqlRepository(sqlClient)
}

class BigDecimalPostgresqlRepository(sqlClient: PostgresqlReactorSqlClient) :
    ReactorSelectBigDecimalRepository<PostgresqlBigDecimals>(sqlClient, PostgresqlBigDecimals)

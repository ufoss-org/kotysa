/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericTest

class R2DbcSelectBigDecimalAsNumericPostgresqlTest :
    AbstractR2dbcPostgresqlTest<BigDecimalAsNumericPostgresqlRepository>(),
    ReactorSelectBigDecimalAsNumericTest<PostgresqlBigDecimalAsNumerics, BigDecimalAsNumericPostgresqlRepository,
            ReactorTransaction> {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = BigDecimalAsNumericPostgresqlRepository(sqlClient)
}

class BigDecimalAsNumericPostgresqlRepository(sqlClient: PostgresqlReactorSqlClient) :
    ReactorSelectBigDecimalAsNumericRepository<PostgresqlBigDecimalAsNumerics>(
        sqlClient,
        PostgresqlBigDecimalAsNumerics
    )

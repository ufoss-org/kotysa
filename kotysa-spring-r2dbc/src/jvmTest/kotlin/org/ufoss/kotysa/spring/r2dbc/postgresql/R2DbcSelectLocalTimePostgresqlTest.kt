/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlLocalTimes
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalTimeRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalTimeTest

class R2dbcSelectLocalTimePostgresqlTest : AbstractR2dbcPostgresqlTest<LocalTimeRepositoryPostgresqlSelect>(),
    ReactorSelectLocalTimeTest<PostgresqlLocalTimes, LocalTimeRepositoryPostgresqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        LocalTimeRepositoryPostgresqlSelect(sqlClient)
}

class LocalTimeRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalTimeRepository<PostgresqlLocalTimes>(
        sqlClient,
        PostgresqlLocalTimes
    )

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlLocalDates
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTest

class R2dbcSelectLocalDatePostgresqlTest : AbstractR2dbcPostgresqlTest<LocalDateRepositoryPostgresqlSelect>(),
    ReactorSelectLocalDateTest<PostgresqlLocalDates, LocalDateRepositoryPostgresqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        LocalDateRepositoryPostgresqlSelect(sqlClient)
}

class LocalDateRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateRepository<PostgresqlLocalDates>(sqlClient, PostgresqlLocalDates)

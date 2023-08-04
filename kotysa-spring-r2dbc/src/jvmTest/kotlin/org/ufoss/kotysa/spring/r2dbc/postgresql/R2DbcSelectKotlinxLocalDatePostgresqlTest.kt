/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalDates
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTest

class R2dbcSelectKotlinxLocalDatePostgresqlTest :
    AbstractR2dbcPostgresqlTest<KotlinxLocalDateRepositoryPostgresqlSelect>(),
    ReactorSelectKotlinxLocalDateTest<PostgresqlKotlinxLocalDates, KotlinxLocalDateRepositoryPostgresqlSelect,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        KotlinxLocalDateRepositoryPostgresqlSelect(sqlClient)
}

class KotlinxLocalDateRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateRepository<PostgresqlKotlinxLocalDates>(sqlClient, PostgresqlKotlinxLocalDates)

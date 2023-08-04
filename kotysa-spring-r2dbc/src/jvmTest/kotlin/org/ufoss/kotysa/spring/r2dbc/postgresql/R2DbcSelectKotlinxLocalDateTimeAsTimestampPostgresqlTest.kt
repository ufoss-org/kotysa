/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectKotlinxLocalDateTimeAsTimestampTest

class R2dbcSelectKotlinxLocalDateTimeAsTimestampPostgresqlTest :
    AbstractR2dbcPostgresqlTest<KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect>(),
    ReactorSelectKotlinxLocalDateTimeAsTimestampTest<PostgresqlKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient)
}

class KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectKotlinxLocalDateTimeAsTimestampRepository<PostgresqlKotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        PostgresqlKotlinxLocalDateTimeAsTimestamps
    )

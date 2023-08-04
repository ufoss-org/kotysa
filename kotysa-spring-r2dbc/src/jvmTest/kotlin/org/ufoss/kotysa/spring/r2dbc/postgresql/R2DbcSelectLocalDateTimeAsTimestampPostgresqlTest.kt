/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.PostgresqlLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLocalDateTimeAsTimestampTest

class R2dbcSelectLocalDateTimeAsTimestampPostgresqlTest :
    AbstractR2dbcPostgresqlTest<LocalDateTimeAsTimestampRepositoryPostgresqlSelect>(),
    ReactorSelectLocalDateTimeAsTimestampTest<PostgresqlLocalDateTimeAsTimestamps,
            LocalDateTimeAsTimestampRepositoryPostgresqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlReactorSqlClient, coSqlClient: PostgresqlCoroutinesSqlClient) =
        LocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient)
}

class LocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLocalDateTimeAsTimestampRepository<PostgresqlLocalDateTimeAsTimestamps>(
        sqlClient,
        PostgresqlLocalDateTimeAsTimestamps
    )

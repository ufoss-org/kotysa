/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeAsTimestampTest


class R2dbcSelectKotlinxLocalDateTimeAsTimestampPostgresqlTest :
    AbstractR2dbcPostgresqlTest<KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect>(),
    CoroutinesSelectKotlinxLocalDateTimeAsTimestampTest<PostgresqlKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) =
        KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient)
}

class KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalDateTimeAsTimestampRepository<PostgresqlKotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        PostgresqlKotlinxLocalDateTimeAsTimestamps
    )

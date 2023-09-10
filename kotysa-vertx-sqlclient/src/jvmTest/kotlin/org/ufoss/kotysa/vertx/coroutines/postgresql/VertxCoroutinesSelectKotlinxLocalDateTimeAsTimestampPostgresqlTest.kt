/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTimeAsTimestampTest


class VertxCoroutinesSelectKotlinxLocalDateTimeAsTimestampPostgresqlTest :
    AbstractVertxCoroutinesPostgresqlTest<KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect>(),
    CoroutinesSelectKotlinxLocalDateTimeAsTimestampTest<PostgresqlKotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient)
}

class KotlinxLocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectKotlinxLocalDateTimeAsTimestampRepository<PostgresqlKotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        PostgresqlKotlinxLocalDateTimeAsTimestamps
    )

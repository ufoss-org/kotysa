/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTimeAsTimestampTest


class VertxCoroutinesSelectLocalDateTimeAsTimestampPostgresqlTest :
    AbstractVertxCoroutinesPostgresqlTest<LocalDateTimeAsTimestampRepositoryPostgresqlSelect>(),
    CoroutinesSelectLocalDateTimeAsTimestampTest<PostgresqlLocalDateTimeAsTimestamps,
            LocalDateTimeAsTimestampRepositoryPostgresqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        LocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient)
}

class LocalDateTimeAsTimestampRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLocalDateTimeAsTimestampRepository<PostgresqlLocalDateTimeAsTimestamps>(
        sqlClient,
        PostgresqlLocalDateTimeAsTimestamps,
    )

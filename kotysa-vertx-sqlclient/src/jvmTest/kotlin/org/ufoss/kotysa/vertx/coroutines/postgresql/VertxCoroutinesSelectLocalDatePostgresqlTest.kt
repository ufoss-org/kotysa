/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLocalDateTest


class VertxCoroutinesSelectLocalDatePostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<LocalDateRepositoryPostgresqlSelect>(),
    CoroutinesSelectLocalDateTest<PostgresqlLocalDates, LocalDateRepositoryPostgresqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        LocalDateRepositoryPostgresqlSelect(sqlClient)
}

class LocalDateRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLocalDateRepository<PostgresqlLocalDates>(sqlClient, PostgresqlLocalDates)

/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectTsvectorRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectTsvectorTest

class VertxCoroutinesSelectTsvectorPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<TsvectorRepositoryPostgresqlSelect>(),
    CoroutinesSelectTsvectorTest<TsvectorRepositoryPostgresqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) = TsvectorRepositoryPostgresqlSelect(sqlClient)
}

class TsvectorRepositoryPostgresqlSelect(sqlClient: PostgresqlCoroutinesVertxSqlClient) : CoroutinesSelectTsvectorRepository(sqlClient)

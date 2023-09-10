/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntTest

@Order(1)
class VertxCoroutinesSelectIntPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<SelectIntRepositoryPostgresqlSelect>(),
    CoroutinesSelectIntTest<PostgresqlInts, SelectIntRepositoryPostgresqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        SelectIntRepositoryPostgresqlSelect(sqlClient)
}

class SelectIntRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectIntRepository<PostgresqlInts>(sqlClient, PostgresqlInts)

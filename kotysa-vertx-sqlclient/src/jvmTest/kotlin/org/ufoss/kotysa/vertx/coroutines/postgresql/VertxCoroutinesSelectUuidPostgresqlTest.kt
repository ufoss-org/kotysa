/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlUuids
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectUuidRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectUuidTest

class VertxCoroutinesSelectUuidPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<UuidRepositoryPostgresqlSelect>(),
    CoroutinesSelectUuidTest<PostgresqlUuids, UuidRepositoryPostgresqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) = UuidRepositoryPostgresqlSelect(sqlClient)
}

class UuidRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectUuidRepository<PostgresqlUuids>(sqlClient, PostgresqlUuids)

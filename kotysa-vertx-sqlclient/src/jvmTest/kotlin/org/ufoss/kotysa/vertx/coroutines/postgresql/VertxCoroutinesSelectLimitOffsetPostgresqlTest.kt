/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetTest

class VertxCoroutinesSelectLimitOffsetPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<LimitOffsetRepositoryPostgresqlSelect>(),
    CoroutinesSelectLimitOffsetTest<PostgresqlCustomers, LimitOffsetRepositoryPostgresqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        LimitOffsetRepositoryPostgresqlSelect(sqlClient)
}

class LimitOffsetRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLimitOffsetRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)

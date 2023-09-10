/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import io.vertx.pgclient.PgException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertTest

@Order(3)
class VertxCoroutinesInsertPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<RepositoryPostgresqlInsert>(),
    CoroutinesInsertTest<PostgresqlInts, PostgresqlLongs, PostgresqlCustomers, RepositoryPostgresqlInsert, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) = RepositoryPostgresqlInsert(sqlClient)
    override val exceptionClass = PgException::class.java
}

class RepositoryPostgresqlInsert(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesInsertRepository<PostgresqlInts, PostgresqlLongs, PostgresqlCustomers>(sqlClient, PostgresqlInts, PostgresqlLongs, PostgresqlCustomers)

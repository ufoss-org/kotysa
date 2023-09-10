/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectGroupByTest

class VertxCoroutinesSelectGroupByPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<GroupByRepositoryPostgresqlSelect>(),
    CoroutinesSelectGroupByTest<PostgresqlCustomers, GroupByRepositoryPostgresqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        GroupByRepositoryPostgresqlSelect(sqlClient)
}

class GroupByRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectGroupByRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)

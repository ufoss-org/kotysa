/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByTest

class VertxCoroutinesSelectOrderByPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<OrderByRepositoryPostgresqlSelect>(),
    CoroutinesSelectOrderByTest<PostgresqlCustomers, OrderByRepositoryPostgresqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        OrderByRepositoryPostgresqlSelect(sqlClient)
}

class OrderByRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectOrderByRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)

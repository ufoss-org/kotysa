/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectMinMaxAvgSumTest

class VertxCoroutinesSelectMinMaxAvgSumPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<MinMaxAvgSumRepositoryPostgresqlSelect>(),
    CoroutinesSelectMinMaxAvgSumTest<PostgresqlCustomers, MinMaxAvgSumRepositoryPostgresqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectMinMaxAvgSumRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)

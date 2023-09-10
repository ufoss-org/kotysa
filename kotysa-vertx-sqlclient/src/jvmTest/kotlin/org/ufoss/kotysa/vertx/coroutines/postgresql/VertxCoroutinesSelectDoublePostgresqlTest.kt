/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlDoubles
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleTest

class VertxCoroutinesSelectDoublePostgresqlTest :
    AbstractVertxCoroutinesPostgresqlTest<DoubleRepositoryPostgresqlSelect>(),
    CoroutinesSelectDoubleTest<PostgresqlDoubles, DoubleRepositoryPostgresqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        DoubleRepositoryPostgresqlSelect(sqlClient)
}

class DoubleRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectDoubleRepository<PostgresqlDoubles>(sqlClient, PostgresqlDoubles)

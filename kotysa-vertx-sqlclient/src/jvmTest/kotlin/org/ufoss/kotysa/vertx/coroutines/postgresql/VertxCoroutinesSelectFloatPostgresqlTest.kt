/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlFloats
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatTest

class VertxCoroutinesSelectFloatPostgresqlTest :
    AbstractVertxCoroutinesPostgresqlTest<FloatRepositoryPostgresqlSelect>(),
    CoroutinesSelectFloatTest<PostgresqlFloats, FloatRepositoryPostgresqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        FloatRepositoryPostgresqlSelect(sqlClient)
}

class FloatRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectFloatRepository<PostgresqlFloats>(sqlClient, PostgresqlFloats)

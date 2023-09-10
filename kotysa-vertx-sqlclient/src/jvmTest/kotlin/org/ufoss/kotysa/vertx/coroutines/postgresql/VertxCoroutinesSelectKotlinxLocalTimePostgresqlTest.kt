/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlKotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalTimeTest

class VertxCoroutinesSelectKotlinxLocalTimePostgresqlTest :
    AbstractVertxCoroutinesPostgresqlTest<KotlinxLocalTimeRepositoryPostgresqlSelect>(),
    CoroutinesSelectKotlinxLocalTimeTest<PostgresqlKotlinxLocalTimes, KotlinxLocalTimeRepositoryPostgresqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        KotlinxLocalTimeRepositoryPostgresqlSelect(sqlClient)
}

class KotlinxLocalTimeRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectKotlinxLocalTimeRepository<PostgresqlKotlinxLocalTimes>(sqlClient, PostgresqlKotlinxLocalTimes)

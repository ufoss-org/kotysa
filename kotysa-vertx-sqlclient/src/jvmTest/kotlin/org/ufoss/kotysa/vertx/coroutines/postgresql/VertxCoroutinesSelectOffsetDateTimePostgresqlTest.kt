/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlOffsetDateTimes
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOffsetDateTimeTest

class VertxCoroutinesSelectOffsetDateTimePostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<OffsetDateTimeRepositoryPostgresqlSelect>(),
    CoroutinesSelectOffsetDateTimeTest<PostgresqlOffsetDateTimes, OffsetDateTimeRepositoryPostgresqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        OffsetDateTimeRepositoryPostgresqlSelect(sqlClient)
}

class OffsetDateTimeRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectOffsetDateTimeRepository<PostgresqlOffsetDateTimes>(sqlClient, PostgresqlOffsetDateTimes)

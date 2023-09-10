/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlByteArrayAsByteas
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectByteArrayAsBinaryTest

class VertxCoroutinesSelectByteArrayAsByteaPostgresqlTest :
    AbstractVertxCoroutinesPostgresqlTest<ByteArrayAsByteaRepositoryPostgresqlSelect>(),
    CoroutinesSelectByteArrayAsBinaryTest<PostgresqlByteArrayAsByteas, ByteArrayAsByteaRepositoryPostgresqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        ByteArrayAsByteaRepositoryPostgresqlSelect(sqlClient)
}

class ByteArrayAsByteaRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectByteArrayAsBinaryRepository<PostgresqlByteArrayAsByteas>(sqlClient, PostgresqlByteArrayAsByteas)

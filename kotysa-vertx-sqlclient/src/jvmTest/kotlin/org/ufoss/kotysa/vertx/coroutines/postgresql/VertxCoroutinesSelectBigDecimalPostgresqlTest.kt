/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlBigDecimals
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalTest

class VertxCoroutinesSelectBigDecimalPostgresqlTest :
    AbstractVertxCoroutinesPostgresqlTest<BigDecimalRepositoryPostgresqlSelect>(),
    CoroutinesSelectBigDecimalTest<PostgresqlBigDecimals, BigDecimalRepositoryPostgresqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        BigDecimalRepositoryPostgresqlSelect(sqlClient)
}

class BigDecimalRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBigDecimalRepository<PostgresqlBigDecimals>(sqlClient, PostgresqlBigDecimals)

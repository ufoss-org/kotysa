/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericTest

class VertxCoroutinesSelectBigDecimalAsNumericPostgresqlTest :
    AbstractVertxCoroutinesPostgresqlTest<BigDecimalAsNumericRepositoryPostgresqlSelect>(),
    CoroutinesSelectBigDecimalAsNumericTest<PostgresqlBigDecimalAsNumerics, BigDecimalAsNumericRepositoryPostgresqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) =
        BigDecimalAsNumericRepositoryPostgresqlSelect(sqlClient)
}

class BigDecimalAsNumericRepositoryPostgresqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBigDecimalAsNumericRepository<PostgresqlBigDecimalAsNumerics>(sqlClient, PostgresqlBigDecimalAsNumerics)

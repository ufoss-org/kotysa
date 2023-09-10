/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericTest

class VertxCoroutinesSelectBigDecimalAsNumericMariadbTest : AbstractVertxCoroutinesMariadbTest<BigDecimalAsNumericRepositoryMariadbSelect>(),
    CoroutinesSelectBigDecimalAsNumericTest<MariadbBigDecimalAsNumerics, BigDecimalAsNumericRepositoryMariadbSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = BigDecimalAsNumericRepositoryMariadbSelect(sqlClient)
}

class BigDecimalAsNumericRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBigDecimalAsNumericRepository<MariadbBigDecimalAsNumerics>(sqlClient, MariadbBigDecimalAsNumerics)

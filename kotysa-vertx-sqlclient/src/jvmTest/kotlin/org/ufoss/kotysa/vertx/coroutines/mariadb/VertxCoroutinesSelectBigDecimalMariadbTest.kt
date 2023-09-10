/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbBigDecimals
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalTest

class VertxCoroutinesSelectBigDecimalMariadbTest : AbstractVertxCoroutinesMariadbTest<BigDecimalRepositoryMariadbSelect>(),
    CoroutinesSelectBigDecimalTest<MariadbBigDecimals, BigDecimalRepositoryMariadbSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = BigDecimalRepositoryMariadbSelect(sqlClient)
}

class BigDecimalRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBigDecimalRepository<MariadbBigDecimals>(sqlClient, MariadbBigDecimals)

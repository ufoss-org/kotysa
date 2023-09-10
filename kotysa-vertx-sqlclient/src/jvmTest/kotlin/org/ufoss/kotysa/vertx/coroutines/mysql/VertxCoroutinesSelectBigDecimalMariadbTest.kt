/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlBigDecimals
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalTest

class VertxCoroutinesSelectBigDecimalMysqlTest : AbstractVertxCoroutinesMysqlTest<BigDecimalRepositoryMysqlSelect>(),
    CoroutinesSelectBigDecimalTest<MysqlBigDecimals, BigDecimalRepositoryMysqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = BigDecimalRepositoryMysqlSelect(sqlClient)
}

class BigDecimalRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBigDecimalRepository<MysqlBigDecimals>(sqlClient, MysqlBigDecimals)

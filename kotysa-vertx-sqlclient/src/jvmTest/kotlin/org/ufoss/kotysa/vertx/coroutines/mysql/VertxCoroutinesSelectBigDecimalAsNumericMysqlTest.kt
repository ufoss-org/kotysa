/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericTest

class VertxCoroutinesSelectBigDecimalAsNumericMysqlTest : AbstractVertxCoroutinesMysqlTest<BigDecimalAsNumericRepositoryMysqlSelect>(),
    CoroutinesSelectBigDecimalAsNumericTest<MysqlBigDecimalAsNumerics, BigDecimalAsNumericRepositoryMysqlSelect,
            Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = BigDecimalAsNumericRepositoryMysqlSelect(sqlClient)
}

class BigDecimalAsNumericRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBigDecimalAsNumericRepository<MysqlBigDecimalAsNumerics>(sqlClient, MysqlBigDecimalAsNumerics)
